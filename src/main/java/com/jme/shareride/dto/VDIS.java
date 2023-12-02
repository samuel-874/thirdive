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
public class VDIS {
    private long id;
    private String vehicleName;
    private ImageData imageData;
}
