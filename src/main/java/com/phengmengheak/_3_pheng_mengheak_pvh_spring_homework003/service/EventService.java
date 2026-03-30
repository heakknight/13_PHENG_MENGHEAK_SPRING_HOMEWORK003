package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Event;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.EventRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents(@Positive Integer page, @Positive Integer size);

    Event getEventById(Long eventId);

    Event createEvent(@Valid EventRequest eventRequest);

    Event updateEvent(Long eventId, @Valid EventRequest eventRequest);

    void deleteEvent(Long eventId);
}
