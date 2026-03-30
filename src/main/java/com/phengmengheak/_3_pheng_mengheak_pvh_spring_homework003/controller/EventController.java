package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.controller;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Event;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.EventRequest;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.response.ApiResponse;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service.EventService;
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
@RequestMapping("api/v1/events")
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventService eventService;

    @Operation(summary = "Get all Events!")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Event>>> getAll(
            @Positive @RequestParam(defaultValue = "1") Integer page,
            @Positive @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(ApiResponse.<List<Event>>builder()
                .success(true).status(HttpStatus.OK.value()).message("Retrieved events successfully!")
                .payload(eventService.getAllEvents(page, size))
                .timestamp(Instant.now()).build());
    }

    @Operation(summary = "Get Event by ID")
    @GetMapping("/{event-id}")
    public ResponseEntity<ApiResponse<Event>> getEventById(@PathVariable("event-id") Long eventId) {
        Event event = eventService.getEventById(eventId);

        ApiResponse<Event> response = ApiResponse.<Event>builder()
                .success(true).status(HttpStatus.OK.value())
                .message("Retrieved event successfully!")
                .payload(event).timestamp(Instant.now()).build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new Event")
    @PostMapping
    public ResponseEntity<ApiResponse<Event>> createEvent(@Valid @RequestBody EventRequest eventRequest){
        ApiResponse<Event> response = ApiResponse.<Event>builder()
                .success(true).status(HttpStatus.CREATED.value())
                .message("Event created and attendees registered successfully!")
                .payload(eventService.createEvent(eventRequest))
                .timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update Event by ID")
    @PutMapping("/{event-id}")
    public ResponseEntity<ApiResponse<Event>> updateEvent(@PathVariable("event-id") Long eventId,
                                                          @Valid @RequestBody EventRequest eventRequest) {
        ApiResponse<Event> response = ApiResponse.<Event>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Updated Event successfully!")
                .payload(eventService.updateEvent(eventId, eventRequest))
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete Event by ID")
    @DeleteMapping("/{event-id}")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable("event-id") Long eventId) {
        eventService.deleteEvent(eventId);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Event and its attendees deleted successfully!")
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.ok(response);
    }

}