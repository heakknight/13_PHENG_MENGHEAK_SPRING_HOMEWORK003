package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service.impl;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.exception.NotFoundException;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Event;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.EventRequest;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository.AttendeeRepository;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository.EventAttendeeRepository;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository.EventRepository;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository.VenueRepository;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final AttendeeRepository attendeeRepository;
    private final EventAttendeeRepository eventAttendeeRepository;

    @Override
    public List<Event> getAllEvents(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<Event> events = eventRepository.getAllEvents(offset, size);

        for (Event e : events) {
            e.setAttendees(eventAttendeeRepository.getAttendeesByEventId(e.getEventId().intValue()));
        }
        return events;
    }

    @Override
    public Event getEventById(Long eventId) {
        Event event = eventRepository.getEventById(eventId);
        if (event == null) {
            throw new NotFoundException("Event with ID " + eventId + " was not found!");
        }
        return event;
    }

    @Override
    public Event createEvent(EventRequest eventRequest) {
        if (venueRepository.getVenueById(eventRequest.getVenueId()) == null) {
            throw new NotFoundException("Venue ID " + eventRequest.getVenueId() + " not found!");
        }
        if (eventRequest.getAttendeeIds() != null) {
            for (Long aId : eventRequest.getAttendeeIds()) {
                if (attendeeRepository.getAttendeeById(aId) == null) {
                    throw new NotFoundException("Attendee ID " + aId + " does not exist!");
                }
            }
        }
        Long eventId = eventRepository.createEvent(eventRequest);
        if (eventRequest.getAttendeeIds() != null) {
            for (Long aId : eventRequest.getAttendeeIds()) {
                eventAttendeeRepository.registerAttendeeToEvent(aId, eventId);
            }
        }
        return getEventById(eventId);
    }

    @Override
    public Event updateEvent(Long eventId, EventRequest eventRequest) {
        if (eventRepository.getEventById(eventId) == null) {
            throw new NotFoundException("Event with ID " + eventId + " not found!");
        }
        if (venueRepository.getVenueById(eventRequest.getVenueId()) == null) {
            throw new NotFoundException("Venue ID " + eventRequest.getVenueId() + " not found!");
        }

        eventRepository.updateEvent(eventId, eventRequest);
        eventAttendeeRepository.deleteAttendeesByEventId(eventId);

        if (eventRequest.getAttendeeIds() != null && !eventRequest.getAttendeeIds().isEmpty()) {
            for (Long aId : eventRequest.getAttendeeIds()) {
                if (attendeeRepository.getAttendeeById(aId) == null) {
                    throw new NotFoundException("Attendee ID " + aId + " does not exist!");
                }
                eventAttendeeRepository.registerAttendeeToEvent(aId, eventId);
            }
        }

        return getEventById(eventId);
    }

    @Override
    public void deleteEvent(Long eventId) {
        if (eventRepository.getEventById(eventId) == null) {
            throw new NotFoundException("Event with ID " + eventId + " not found!");
        }
        eventAttendeeRepository.deleteAttendeesByEventId(eventId);
        eventRepository.deleteEvent(eventId);
    }
}