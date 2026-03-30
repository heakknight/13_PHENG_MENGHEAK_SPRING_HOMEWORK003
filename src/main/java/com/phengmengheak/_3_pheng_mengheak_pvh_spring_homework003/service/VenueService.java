package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Venue;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.VenueRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface VenueService {
    List<Venue> getAllVenues(Integer page, Integer size);

    Venue getVenueById(Long venueId);

    Venue createVenue(VenueRequest venueRequest);

    Venue updateVenueById(@Valid Long venueId, @Valid VenueRequest venueRequest);

    Venue deleteVenueById(Long venueId);
}
