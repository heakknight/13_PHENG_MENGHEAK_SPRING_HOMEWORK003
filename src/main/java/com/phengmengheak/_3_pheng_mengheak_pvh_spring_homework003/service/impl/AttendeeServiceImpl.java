package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service.impl;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.exception.ConflictException;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.exception.EmailDuplicateException;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.exception.NotFoundException;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.exception.UserDuplicateException;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Attendee;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.AttendeeRequest;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.repository.AttendeeRepository;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service.AttendeeService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendeeServiceImpl implements AttendeeService {
    private final AttendeeRepository attendeeRepository;

    @Override
    public List<Attendee> getAllAttendees(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        return attendeeRepository.getAllAttendees(offset,size);
    }

    @Override
    public Attendee getAttendeeById(@Positive Long attendeeId) {
        Attendee attendee = attendeeRepository.getAttendeeById(attendeeId);
        if(attendee == null){
            throw new NotFoundException("Attendee with the"+attendeeId+"not FOUND!");
        }
        return attendeeRepository.getAttendeeById(attendeeId);
    }

    @Override
    public Attendee createAttendee(AttendeeRequest attendeeRequest) {
        if(attendeeRepository.attendeeName(attendeeRequest.getAttendeeName())){
            throw new UserDuplicateException("Attendee name already exist!");
        }
        if(attendeeRepository.attendeeEmail(attendeeRequest.getEmail())){
            throw new EmailDuplicateException("Email Attendee already exist!");
        }
        return attendeeRepository.createAttendee(attendeeRequest);
    }

    @Override
    public Attendee updateAttendeeById(Long attendeeId, AttendeeRequest attendeeRequest) {
        Attendee attendee = attendeeRepository.getAttendeeById(attendeeId);
        if(attendee == null){
            throw new NotFoundException("Attendee with the"+attendeeId+"not FOUND!");
        }
        if(attendeeRepository.attendeeName(attendeeRequest.getAttendeeName())){
            throw new UserDuplicateException("Attendee name already exist!");
        }
        if(attendeeRepository.attendeeEmail(attendeeRequest.getEmail())){
            throw new EmailDuplicateException("Email Attendee already exist!");
        }

        return attendeeRepository.updateAttendeeById(attendeeId,attendeeRequest);
    }

    @Override
    public void deleteAttendeeById(Long attendeeId) {
        Attendee attendee = attendeeRepository.getAttendeeById(attendeeId);
        if (attendee == null) {
            throw new NotFoundException("Attendee with id " + attendeeId + " not found!");
        }
        if (attendeeRepository.isAttendeeInAnyEvent(attendeeId)) {
            throw new ConflictException("This attendee join some events. Remove from events first.");
        }

        attendeeRepository.deleteAttendeeById(attendeeId);
    }
}
