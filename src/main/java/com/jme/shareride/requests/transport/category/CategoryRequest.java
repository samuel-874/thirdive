package com.jme.shareride.requests.transport.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    private long id;
    private String categoryName;
    private String image;
}
