package com.vetcaller.controller.appointment.dto.request;

import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record CreateAppointmentRequest (
        @NotNull
        Timestamp dateStart,

        @NotNull
        Timestamp dateEnd,

        @NotNull
        Long petId,

        @NotNull
        Long ownerId,

        @NotNull
        Long vetId
)
{}
