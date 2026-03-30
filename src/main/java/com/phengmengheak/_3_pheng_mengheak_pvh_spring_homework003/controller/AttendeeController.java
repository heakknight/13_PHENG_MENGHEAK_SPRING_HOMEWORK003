package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.controller;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Attendee;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Venue;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.AttendeeRequest;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.VenueRequest;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.response.ApiResponse;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service.AttendeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/attendees")
@RequiredArgsConstructor
@Validated
public class AttendeeController {
    private final AttendeeService attendeeService;

    @Operation(summary = "Get all attendees")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Attendee>>> getAllAttendees(
            @Positive @RequestParam(defaultValue = "1")  Integer page,
            @Positive @RequestParam(defaultValue = "10") Integer size){
        ApiResponse<List<Attendee>> response = ApiResponse.<List<Attendee>>builder()
                .success(true).status(HttpStatus.OK.value()).message("Get attendees successfully!")
                .payload(attendeeService.getAllAttendees(page,size)).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get attendee by ID")
    @GetMapping("/{attendee-id}")
    public ResponseEntity<ApiResponse<Attendee>> getAttendeeById(@PathVariable("attendee-id") Long attendeeId){
        ApiResponse<Attendee>
                response = ApiResponse.<Attendee>builder().
                success(true).status(HttpStatus.OK.value()).
                message("Retrieved Attendee successfully!").
                payload(attendeeService.getAttendeeById(attendeeId)).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Create new Attendee")
    @PostMapping
    public ResponseEntity<ApiResponse<Attendee>> createNewAttendee(@Valid @RequestBody AttendeeRequest attendeeRequest){
        ApiResponse<Attendee>
                response = ApiResponse.<Attendee>builder().
                success(true).status(HttpStatus.CREATED.value()).
                message("Created Attendee successfully!").
                payload(attendeeService.createAttendee(attendeeRequest)).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Update Attendee by ID")
    @PutMapping("/{attendee-id}")
    public ResponseEntity<ApiResponse<Attendee>> updateAttendeeById(@PathVariable("attendee-id") Long attendeeId,
                                                                    @RequestBody AttendeeRequest attendeeRequest){
        ApiResponse<Attendee>
                response = ApiResponse.<Attendee>builder().
                success(true).status(HttpStatus.OK.value()).
                message("Updated Attendee successfully!").
                payload(attendeeService.updateAttendeeById(attendeeId,attendeeRequest)).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete Attendee by ID")
    @DeleteMapping("/{attendee-id}")
    public ResponseEntity<ApiResponse<String>> deleteAttendeeById(@PathVariable("attendee-id") Long attendeeId) {
        attendeeService.deleteAttendeeById(attendeeId);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted Attendee successfully!")
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
