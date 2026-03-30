package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Long eventId;
    private String eventName;
    private Instant eventDate;
    private Venue venue;
    private List<Attendee> attendees;
}
