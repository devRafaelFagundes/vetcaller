package com.vetcaller.service;

import com.vetcaller.controller.appointment.dto.response.GetAppointmentResponse;
import com.vetcaller.domain.Appointment;
import com.vetcaller.exceptions.ResourceNotFoundException;
import com.vetcaller.mapper.AppointmentMapper;
import com.vetcaller.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public Page<GetAppointmentResponse> getAppointments(Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findAll(pageable);
        if(appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found");
        }
        return appointments.map(AppointmentMapper::toGetAppointmentResponse);
    }
}
