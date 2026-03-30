package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.controller;

import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity.Venue;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request.VenueRequest;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.response.ApiResponse;
import com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/venues")
@RequiredArgsConstructor
@Validated
public class VenueController {
    private final VenueService venueService;

    @Operation(summary = "Get all Venues!")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Venue>>> getAllVenues(
            @Positive @RequestParam(defaultValue = "1")  Integer page,
            @Positive @RequestParam(defaultValue = "10") Integer size){
        ApiResponse<List<Venue>> response = ApiResponse.<List<Venue>>builder().success(true).status(HttpStatus.OK.value()).message("Venues fetched successfully!").payload(venueService.getAllVenues(page,size)).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get Venue by ID")
    @GetMapping("/{venue-id}")
    public ResponseEntity<ApiResponse<Venue>> getVenueById(@PathVariable("venue-id") Long venueId){
        ApiResponse<Venue>
            response = ApiResponse.<Venue>builder().
                success(true).status(HttpStatus.OK.value()).
                message("Retrieved Venue successfully!").
                payload(venueService.getVenueById(venueId)).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Create new Venue")
    @PostMapping
    public ResponseEntity<ApiResponse<Venue>> createNewVenue(@Valid @RequestBody VenueRequest venueRequest){
        ApiResponse<Venue>
            response = ApiResponse.<Venue>builder().
                    success(true).status(HttpStatus.CREATED.value()).
                    message("Created Venue successfully!").
                    payload(venueService.createVenue(venueRequest)).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Update Venue by ID")
    @PutMapping("/{venue-id}")
    public ResponseEntity<ApiResponse<Venue>> updateVenueById(@PathVariable("venue-id") Long venueId,
                                                              @Valid @RequestBody VenueRequest venueRequest){
        ApiResponse<Venue>
                response = ApiResponse.<Venue>builder().
                success(true).status(HttpStatus.OK.value()).
                message("Updated Venue id " +venueId+ " successfully!").
                payload(venueService.updateVenueById(venueId,venueRequest)).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete Venue by ID")
    @DeleteMapping("/{venue-id}")
    public ResponseEntity<ApiResponse<Venue>> deleteVenueById(@PathVariable("venue-id") Long venueId){
        ApiResponse<Venue>
                response = ApiResponse.<Venue>builder().
                success(true).status(HttpStatus.OK.value()).
                message("Deleted Venue id " +venueId+ " successfully!").
                payload(venueService.deleteVenueById(venueId)).timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}