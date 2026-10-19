package com.vetcaller.validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppointmentValidator {

    private final UserValidator userValidator;
    private final PetValidator petValidator;

    //methods
}
