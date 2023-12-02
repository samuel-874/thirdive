package com.jme.shareride.requests.transport.rent;

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
public class RequestForRent {
    private long vehicleId;
    private LocalDate date;
    private LocalTime time;
}
