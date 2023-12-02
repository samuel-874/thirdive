package com.jme.shareride.dto;

import com.jme.shareride.entity.enums.Gear;
import com.jme.shareride.entity.others.ImageData;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRegistrationDto {
    private long id;
    private String vehicleName;
    private String maxPower;
    private String fuelDurability;
    private String maxSpeed;
    private String mph;
    private String color;
    private String fuelType;
    private Gear gearType;
    private int seatCount;
    private UserEntity driver;
    private ImageData image;
    private int chargePerHour;


}
