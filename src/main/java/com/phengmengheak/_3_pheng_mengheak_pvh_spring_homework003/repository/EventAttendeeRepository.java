package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Attendee;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Event;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EventAttendeeRepository {
    @Results({
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name"),
    })
    @Select("""
            SELECT a.* FROM attendees a
            JOIN event_attendee ea ON a.attendee_id = ea.attendee_id
            WHERE ea.event_id = #{eventId}
            """)
    List<Attendee> getAttendeesByEventId(Integer eventId);

    @Insert("INSERT INTO event_attendee (event_id, attendee_id) VALUES (#{eventId}, #{attendeeId})")
    void registerAttendeeToEvent(@Param("attendeeId") Long attendeeId, @Param("eventId") Long eventId);

    @Delete("DELETE FROM event_attendee WHERE event_id = #{eventId}")
    void deleteAttendeesByEventId(@Param("eventId") Long eventId);
}
