package com.jme.shareride.dto;


import com.jme.shareride.entity.enums.Gear;
import com.jme.shareride.entity.others.ImageData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayInList {
    private long id;
    private String vehicleName;
    private ImageData imageData;
    private String fuelType;
    private Gear gearType;
    private int seatCount;
    private String distanceBetweenUserAndDriver;
    private String timeDifferenceBetweenUserAndDriver;


}
