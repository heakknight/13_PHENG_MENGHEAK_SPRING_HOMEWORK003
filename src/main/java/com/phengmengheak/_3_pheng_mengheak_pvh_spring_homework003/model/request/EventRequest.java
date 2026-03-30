package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    @NotBlank(message = "Event name cannot be blank")
    @Size(max = 255)
    private String eventName;

    @NotNull(message = "Event date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    @NotNull(message = "Venue ID is required")
    @Positive(message = "Venue ID must be a positive number")
    private Long venueId;

    @NotEmpty(message = "At least one attendee is required")
    private List<Long> attendeeIds;
}
