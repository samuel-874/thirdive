package com.jme.shareride.external.google.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistanceResponse {
    private String[] destination_addresses;
    private String[] origin_addresses;
    private Row[] rows;
    private String status;

}
