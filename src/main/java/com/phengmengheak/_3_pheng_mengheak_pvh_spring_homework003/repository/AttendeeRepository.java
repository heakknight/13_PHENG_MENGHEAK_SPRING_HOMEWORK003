package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Attendee;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.AttendeeRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttendeeRepository {

    @Results(id = "attendeeMapper", value = {
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name"),
    })
    @Select("""
        SELECT * FROM attendees OFFSET #{offset} LIMIT #{size}
    """)
    List<Attendee> getAllAttendees(Integer offset, Integer size);

    @Select("""
        SELECT * FROM attendees WHERE attendee_id = #{attendeeId}
    """)
    @ResultMap("attendeeMapper")
    Attendee getAttendeeById(Long attendeeId);

    @Select("""
           SELECT EXISTS(SELECT 1 FROM attendees WHERE attendee_name = #{attendeeName})
    """)
    Boolean attendeeName(@NotNull @NotBlank(message = "AttendeeName cannot be blank") @Size(min = 1,max=50,message = "AttendeeName should at least1 and max 100!") String attendeeName);

    @Select("""
        SELECT EXISTS(SELECT 1 FROM attendees WHERE email= #{email})
    """)
    Boolean attendeeEmail(@NotNull @NotBlank(message = "AttendeeName's email cannot be blank") @Size(min = 10,max=50,message = "AttendeeName's email at least 10 and max 100!") String email);

    @Select("SELECT EXISTS(SELECT 1 FROM event_attendee WHERE attendee_id = #{attendeeId})")
    Boolean isAttendeeInAnyEvent(Long attendeeId);

    @Select("""
        INSERT INTO attendees VALUES (DEFAULT,#{req.attendeeName},#{req.email}) RETURNING *
    """)
    @ResultMap("attendeeMapper")
    Attendee createAttendee(@Param("req") AttendeeRequest attendeeRequest);

    @Select("""
        UPDATE attendees SET attendee_name = #{req.attendeeName},email = #{req.email} 
        WHERE attendee_id = #{attendeeId} RETURNING *;
    """)
    @ResultMap("attendeeMapper")
    Attendee updateAttendeeById(Long attendeeId,@Param("req") AttendeeRequest attendeeRequest);

    @Delete("""
        DELETE FROM attendees WHERE attendee_id = #{attendeeId}
    """)
    void deleteAttendeeById(Long attendeeId);
}