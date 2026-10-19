package com.vetcaller.service.appointment;

import com.vetcaller.controller.appointment.dto.request.CreateAppointmentRequest;
import com.vetcaller.controller.appointment.dto.response.GenericAppointmentResponse;
import com.vetcaller.domain.Appointment;
import com.vetcaller.domain.User;
import com.vetcaller.domain.enums.AppointmentStatus;
import com.vetcaller.exceptions.ConflitException;
import com.vetcaller.exceptions.ResourceAlreadyInUseException;
import com.vetcaller.exceptions.ResourceNotFoundException;
import com.vetcaller.mapper.AppointmentMapper;
import com.vetcaller.repository.AppointmentRepository;
import com.vetcaller.repository.PetRepository;
import com.vetcaller.validation.AppointmentValidator;
import com.vetcaller.validation.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final UserValidator userValidator;
    private final AppointmentValidator appointmentValidator;

    public Page<GenericAppointmentResponse> getAppointments(Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findAll(pageable);
        if(appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found");
        }
        return appointments.map(AppointmentMapper::toGenericAppointmentResponse);
    }

    @Transactional
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            throw new ConflitException("Only pending appointments can be cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Transactional
    public GenericAppointmentResponse createAppointment(CreateAppointmentRequest request) {

        appointmentValidator.validateAppointmentDates(request.dateStart(), request.dateEnd());
        User vet = userValidator.validateUserById(request.vetId());
        User petOwner = userValidator.validateUserById(request.ownerId());
        userValidator.validateUserHasPetById(request.ownerId(), request.petId());

        if(!appointmentRepository.isAppointmentTimeSlotAvailable(request.vetId(), request.dateStart(), request.dateEnd())) {
            throw new ResourceAlreadyInUseException("The selected time slot is not available");
        }

        Appointment appointment = new Appointment();
        appointment.setVet(vet);
        appointment.setOwner(petOwner);
        appointment.setPet(petRepository.getReferenceById(request.petId()));
        appointment.setDateStart(request.dateStart());
        appointment.setDateEnd(request.dateEnd());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setReason(request.reason());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return AppointmentMapper.toGenericAppointmentResponse(savedAppointment);
    }

    public Page<GenericAppointmentResponse> getAppointmentsByUserId(Long userId, Timestamp afterTimestamp, Pageable pageable) {

        userValidator.validateUserById(userId);

        Page<Appointment> appointments;
        if (afterTimestamp != null) {
            appointments = appointmentRepository.findByVetIdAndStartTimeAfter(userId, afterTimestamp, pageable);
        } else {
            appointments = appointmentRepository.findByVetId(userId, pageable);
        }

        if(appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for user with id " + userId);
        }

        return appointments.map(AppointmentMapper::toGenericAppointmentResponse);
    }
}
