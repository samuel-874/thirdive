package com.jme.shareride.requests.transport.vehicle;

import com.jme.shareride.entity.enums.Gear;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRegRequest {
    private String vehicleName;
    private String category;
    private String maxPower;
    private String image;
    private String fuelDurability;
    private String maxSpeed;
    private String mph;
    private String color;
    private int chargePerHour;
    private int seatCount;
    private Gear gearType;
    private String fuelType;

    //todo modify the service layer to get the session user and set as driver
//    private UserEntity driver;


}
