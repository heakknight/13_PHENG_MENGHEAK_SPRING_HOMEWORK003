package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueRequest {
    @NotNull
    @NotBlank(message = "Venue name cannot be blank")
    @Size(min = 1,max=100,message = "Venue name should at least1 and max 100!")
    private String venueName;

    @NotNull
    @NotBlank(message = "Location cannot be blank")
    @Size(min = 1,max=70,message = "Location should at least1 and max 70!")
    private String location;
}
