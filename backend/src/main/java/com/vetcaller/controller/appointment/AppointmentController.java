package com.vetcaller.controller.appointment;

import com.vetcaller.controller.appointment.dto.response.GetAppointmentResponse;
import com.vetcaller.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointments")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public Page<GetAppointmentResponse> getAppointments(Pageable pageable) {
        return appointmentService.getAppointments(pageable);
    }

}
