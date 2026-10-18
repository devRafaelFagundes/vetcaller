package com.vetcaller.mapper;

import com.vetcaller.controller.appointment.dto.response.GetAppointmentResponse;
import com.vetcaller.domain.Appointment;

public class AppointmentMapper {
    public static GetAppointmentResponse toGetAppointmentResponse(Appointment appointment) {
        return GetAppointmentResponse.builder()
                .id(appointment.getId())
                .date_start(appointment.getDateStart())
                .date_end(appointment.getDateEnd())
                .petId(appointment.getPet().getId())
                .vetId(appointment.getVet().getId())
                .ownerId(appointment.getOwner().getId())
                .build();
    }
}
