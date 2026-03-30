package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Event;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.EventRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EventRepository {

    @Results(id = "eventMapper", value = {
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "eventName", column = "event_name"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "venue", column = "venue_id",
                    one = @One(select = "com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository.VenueRepository.getVenueById")),
            @Result(property = "attendees", column = "event_id",
                    many = @Many(select = "com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository.EventAttendeeRepository.getAttendeesByEventId"))
    })
    @Select("SELECT * FROM events LIMIT #{size} OFFSET #{offset}")
    List<Event> getAllEvents(Integer offset, Integer size);

    @Select("SELECT * FROM events WHERE event_id = #{eventId}")
    @ResultMap("eventMapper")
    Event getEventById(Long eventId);

    @Select("""
        INSERT INTO events
        VALUES (DEFAULT,#{req.eventName}, #{req.eventDate}, #{req.venueId})
        RETURNING event_id
    """)
    Long createEvent(@Param("req")EventRequest eventRequest);

    @Update("""
        UPDATE events SET event_name = #{req.eventName},
                          event_date = #{req.eventDate},
                          venue_id = #{req.venueId}
        WHERE event_id = #{eventId}
    """)
    void updateEvent(@Param("eventId") Long eventId, @Param("req") EventRequest eventRequest);

    @Delete("DELETE FROM events WHERE event_id = #{eventId}")
    void deleteEvent(@Param("eventId") Long eventId);
}
