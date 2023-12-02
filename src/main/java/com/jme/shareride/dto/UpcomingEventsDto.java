package com.jme.shareride.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingEventsDto {
    private long id;
    private VehicleDetails vehicleDetails;
    private LocalDate date;
    private LocalTime time;
    private String pickOffLocation;
    private String dropOffLocation;
    private String username;
    private String distance;
    private String duration;
}
