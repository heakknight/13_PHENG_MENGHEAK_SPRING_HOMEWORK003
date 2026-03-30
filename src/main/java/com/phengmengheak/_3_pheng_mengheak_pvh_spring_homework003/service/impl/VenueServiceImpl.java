package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service.impl;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.exception.ConflictException;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.exception.NotFoundException;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.exception.UserDuplicateException;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Venue;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.VenueRequest;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository.VenueRepository;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;

    @Override
    public List<Venue> getAllVenues(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        return venueRepository.getAllVenues(offset,size);
    }

    @Override
    public Venue getVenueById(Long venueId) {
        Venue venue = venueRepository.getVenueById(venueId);
        if(venue == null){
            throw new NotFoundException("Venue with the"+venueId+"not FOUND!");
        }
        return venueRepository.getVenueById(venueId);
    }

    @Override
    public Venue createVenue(VenueRequest venueRequest) {
        if(venueRepository.venueName(venueRequest.getVenueName())){
            throw new UserDuplicateException("Venue name already exist!");
        }
        return venueRepository.createVenue(venueRequest);
    }

    @Override
    public Venue updateVenueById(Long venueId, VenueRequest venueRequest) {
        Venue venue = venueRepository.getVenueById(venueId);
        if(venue == null){
            throw new NotFoundException("Venue with the"+venueId+"not FOUND!");
        }
        return venueRepository.updateVenueById(venueId,venueRequest);
    }

    @Override
    public Venue deleteVenueById(Long venueId) {
        Venue venue = venueRepository.getVenueById(venueId);
        if(venue == null){
            throw new NotFoundException("Venue with the"+venueId+"not FOUND!");
        }
        if (venueRepository.isVenueInUse(venueId)) {
            throw new ConflictException("Some events still use this venue. Update | delete events first1.");
        }
        return venueRepository.deleteVenueById(venueId);
    }
}
