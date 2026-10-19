package com.vetcaller.validation;

import com.vetcaller.exceptions.UnprocessableEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;

@Component
@AllArgsConstructor
public class AppointmentValidator {

    private final UserValidator userValidator;
    private final PetValidator petValidator;

    public void validateAppointmentDates(Timestamp dateStart, Timestamp dateEnd) {
        if(dateStart.after(dateEnd)) {
            throw new UnprocessableEntityException("Start date must be before end date");
        }
        else if(dateStart.before(new Timestamp(System.currentTimeMillis()))) {
            throw new UnprocessableEntityException("Start date must be in the future");
        }
        else if(dateStart.compareTo(dateEnd) < 30 * 60 * 1000) {
            throw new UnprocessableEntityException("Appointment must be at least 30 minutes long");
        }
    }
}
