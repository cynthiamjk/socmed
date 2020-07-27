package com.cynthia.socmed.comp;

import com.cynthia.socmed.DAO.EventDao;
import com.cynthia.socmed.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class EventValidator implements Validator {

    @Autowired
    EventDao eventDao;

    @Override
    public boolean supports(Class<?> aClass) {

        return Event.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Event event = (Event) o;
        LocalDate ld = LocalDate.now();

        ValidationUtils.rejectIfEmpty(errors, "name", "NotEmpty", "Name cannot be empty");

        ValidationUtils.rejectIfEmpty(errors, "location", "NotEmpty", "You must chose a country");


        if (event.getStartDate().isBefore(ld)) {
            errors.rejectValue("startDate", "startDate", "Start date cannot be prior to actual date");
        }

        if(event.getEndDate().isBefore(event.getStartDate())) {
            errors.rejectValue("endDate", "endDate", "End date cannot be prior to start date");
        }

        if (event.getStartDate().isEqual(event.getEndDate()) && (event.getTimeEnd().isBefore(event.getTimeEnd()))) {
            errors.rejectValue("endTime", "endTime", "End time cannot be prior to start time");
        }



    }
}