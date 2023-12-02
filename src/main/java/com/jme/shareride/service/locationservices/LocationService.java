package com.jme.shareride.service.locationservices;

import com.jme.shareride.entity.user_and_auth.Location;
import org.springframework.stereotype.Service;

@Service
public interface LocationService {
    void saveLocation(Location location);
}
