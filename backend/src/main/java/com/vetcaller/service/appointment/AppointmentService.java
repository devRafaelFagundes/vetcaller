package com.vetcaller.service.appointment;

import com.vetcaller.controller.appointment.dto.request.CreateAppointmentRequest;
import com.vetcaller.controller.appointment.dto.response.GenericAppointmentResponse;
import com.vetcaller.domain.Appointment;
import com.vetcaller.domain.User;
import com.vetcaller.exceptions.ResourceNotFoundException;
import com.vetcaller.mapper.AppointmentMapper;
import com.vetcaller.repository.AppointmentRepository;
import com.vetcaller.validation.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserValidator userValidator;

    public Page<GenericAppointmentResponse> getAppointments(Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findAll(pageable);
        if(appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found");
        }
        return appointments.map(AppointmentMapper::toGenericAppointmentResponse);
    }

    public GenericAppointmentResponse createAppointment(CreateAppointmentRequest request) {

        User vet = userValidator.validateUserById(request.vetId());
        User petOwner = userValidator.validateUserById(request.ownerId());
        userValidator.validateUserHasPetById(request.ownerId(), request.petId());




        return null;
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
