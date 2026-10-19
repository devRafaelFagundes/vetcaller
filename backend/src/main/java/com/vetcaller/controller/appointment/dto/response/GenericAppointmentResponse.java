package com.vetcaller.controller.appointment.dto.response;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record GenericAppointmentResponse(
        Long id,
        Timestamp date_start,
        Timestamp date_end,
        Long petId,
        Long vetId,
        Long ownerId
){}
