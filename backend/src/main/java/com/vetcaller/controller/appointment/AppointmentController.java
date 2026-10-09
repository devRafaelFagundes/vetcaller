package com.vetcaller.controller.appointment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointments")
public class AppointmentController {

    @GetMapping
    public String getAppointments() {
        return "Hello from AppointmentController!";
    }
}
