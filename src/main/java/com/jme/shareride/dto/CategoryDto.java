package com.jme.shareride.dto;

import com.jme.shareride.entity.others.ImageData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private long id;
    private String categoryName;
    private ImageData image;
}
