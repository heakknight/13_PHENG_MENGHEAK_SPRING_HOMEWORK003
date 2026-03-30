package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Venue;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.VenueRequest;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VenueRepository {

    @Results(id = "venueMapper", value = {
            @Result(property = "venueId", column = "venue_id"),
            @Result(property = "venueName", column = "venue_name"),
    })

    @Select("""
        SELECT * FROM venues OFFSET #{offset} LIMIT #{size}
    """)
    List<Venue> getAllVenues(Integer offset, Integer size);


    @Select("""
        SELECT * FROM venues WHERE venue_id = #{venueId}
    """)
    @ResultMap("venueMapper")
    Venue getVenueById(Long venueId);

    @Select("""
        INSERT INTO venues VALUES (DEFAULT, #{req.venueName}, #{req.location}) RETURNING *
    """)
    @ResultMap("venueMapper")
    Venue createVenue(@Param("req") VenueRequest venueRequest);

    @Select("""
           SELECT EXISTS(SELECT 1 FROM venues WHERE venue_name = #{venueName})
    """)
    Boolean venueName(String venueName);

    @Select("""
        UPDATE venues SET venue_name = #{req.venueName}, location = #{req.location} WHERE venue_id = #{venueId} RETURNING *
    """)
    @ResultMap("venueMapper")
    Venue updateVenueById(Long venueId,@Param("req") VenueRequest venueRequest);

    @Select("SELECT EXISTS(SELECT 1 FROM events WHERE venue_id = #{venueId})")
    Boolean isVenueInUse(Long venueId);
    @Select("""
        DELETE FROM venues WHERE venue_id = #{venueId}
    """)
    @ResultMap("venueMapper")
    Venue deleteVenueById(Long venueId);
}
