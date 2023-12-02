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
public class VehicleDto {
    private long id;
    private String vehicleName;
    private String maxPower;
    private String fuelDurability;
    private String maxSpeed;
    private ImageData imageData;
    private String mph;
    private String color;
    private String fuelType;
    private Gear gearType;
    private int seatCount;
    private String distanceBetweenUserAndDriver;
    private String timeDifferenceBetweenUserAndDriver;

    private int distance;
    private UserEntity driver;
    private int chargePerHour;


}
