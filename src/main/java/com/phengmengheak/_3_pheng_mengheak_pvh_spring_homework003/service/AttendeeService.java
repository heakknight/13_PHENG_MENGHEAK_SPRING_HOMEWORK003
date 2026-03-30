package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Attendee;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.AttendeeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface AttendeeService {
    List<Attendee> getAllAttendees(@Positive Integer page, @Positive Integer size);

    Attendee getAttendeeById(@Positive Long attendeeId);

    Attendee createAttendee(@Valid AttendeeRequest attendeeRequest);

    Attendee updateAttendeeById(Long attendeeId, AttendeeRequest attendeeRequest);

    void deleteAttendeeById(Long attendeeId);
}
