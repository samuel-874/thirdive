package com.jme.shareride.dto;

import com.jme.shareride.entity.transport.Vehicle;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
public class OrderDto {
    private long id;
    private VDIS vehicle;
    private String customersName;
    private String pickOffLocation;
    private String dropOffLocation;
    private String distance;
    private String duration;
    private LocalDate date;
    // hh:mm:ss:msms
    private LocalTime time;
    private int charge;
    private int vat;
    private int total;
}
