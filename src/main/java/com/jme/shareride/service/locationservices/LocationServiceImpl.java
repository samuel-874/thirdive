package com.jme.shareride.service.locationservices;


import com.jme.shareride.entity.user_and_auth.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private  LocationRepository locationRepository;
    @Override
    public void saveLocation(Location location) {
        locationRepository.save(location);
    }
}
