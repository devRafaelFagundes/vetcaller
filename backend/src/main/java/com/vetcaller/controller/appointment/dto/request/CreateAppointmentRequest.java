package com.vetcaller.controller.appointment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
        Long vetId,

        @NotBlank
        @Size(max = 500)
        String reason
)
{}
