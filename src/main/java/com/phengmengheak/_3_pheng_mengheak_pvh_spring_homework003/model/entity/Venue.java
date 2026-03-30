package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class    Venue {
    private Long venueId;
    private String venueName;
    private String location;
}
