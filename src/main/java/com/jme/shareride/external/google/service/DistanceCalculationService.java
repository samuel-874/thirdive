package com.jme.shareride.external.google.service;

import com.jme.shareride.external.google.service.model.DistanceResponse;
import com.jme.shareride.external.google.service.model.Element;
import com.jme.shareride.external.google.service.model.Row;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DistanceCalculationService {

    public Element calculate_location(String origin, String destination){
        String GOOGLE_API_KEY = System.getenv("API_KEY");
        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("origins", origin)
                .queryParam("destinations", destination)
                .queryParam("key", GOOGLE_API_KEY);

        ResponseEntity<DistanceResponse> response = restTemplate.getForEntity(builder.toUriString(), DistanceResponse.class);
        if(response.hasBody()){
            DistanceResponse distanceResponse = response.getBody();
            if(distanceResponse != null && distanceResponse.getRows().length > 0){
                Row row = distanceResponse.getRows()[0];
                if(row != null){
                    Element elements = row.getElements()[0];
                    return elements;
                }
            }
        }

         throw new IllegalStateException("Location Not Found");
    }
}
