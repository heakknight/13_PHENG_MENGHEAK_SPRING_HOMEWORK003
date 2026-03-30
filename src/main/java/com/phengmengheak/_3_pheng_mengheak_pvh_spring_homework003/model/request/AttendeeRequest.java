package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeRequest {
    @NotNull
    @NotBlank(message = "AttendeeName cannot be blank")
    @Size(min = 1,max=50,message = "AttendeeName should at least1 and max 100!")
    private String attendeeName;

    @NotNull
    @NotBlank(message = "AttendeeName's email cannot be blank")
    @Size(min = 10,max=50,message = "AttendeeName's email at least 10 and max 100!")
    private String email;
}
