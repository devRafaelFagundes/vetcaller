package com.vetcaller.mapper;

import com.vetcaller.controller.appointment.dto.response.GenericAppointmentResponse;
import com.vetcaller.domain.Appointment;

public class AppointmentMapper {
    public static GenericAppointmentResponse toGenericAppointmentResponse(Appointment appointment) {
        return GenericAppointmentResponse.builder()
                .id(appointment.getId())
                .date_start(appointment.getDateStart())
                .date_end(appointment.getDateEnd())
                .petId(appointment.getPet().getId())
                .vetId(appointment.getVet().getId())
                .ownerId(appointment.getOwner().getId())
                .build();
    }
}
