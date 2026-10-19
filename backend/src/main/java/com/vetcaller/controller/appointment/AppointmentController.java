package com.vetcaller.controller.appointment;

import com.vetcaller.controller.appointment.dto.request.CreateAppointmentRequest;
import com.vetcaller.controller.appointment.dto.response.GenericAppointmentResponse;
import com.vetcaller.domain.enums.AppointmentStatus;
import com.vetcaller.service.appointment.AppointmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("appointments")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public Page<GenericAppointmentResponse> getAppointments(Pageable pageable) {
        return appointmentService.getAppointments(pageable);
    }

    @GetMapping("/vet/{userId}")
    public Page<GenericAppointmentResponse> getAppointments(
            @PathVariable Long userId,
            @RequestParam(required = false) Timestamp afterTimestamp,
            @RequestParam(required = false, defaultValue = "PENDING") AppointmentStatus status,
            Pageable pageable) {
        return appointmentService.getAppointmentsByUserId(userId, afterTimestamp, status, pageable);
    }

    @PostMapping
    public GenericAppointmentResponse createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        return appointmentService.createAppointment(request);
    }

    @DeleteMapping("/{appointmentId}")
    public void cancelAppointment(@PathVariable Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
    }

}
