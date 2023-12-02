package com.jme.shareride.service.rentservices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.dto.RentDto;
import com.jme.shareride.entity.transport.DeliveryInfo;
import com.jme.shareride.entity.transport.Vehicle;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.entity.transport.Rent;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.service.devileryinfoservices.DeliveryInfoRepository;
import com.jme.shareride.service.userServices.UserRepository;
import com.jme.shareride.service.vehicleServices.VehicleRepository;
import com.jme.shareride.requests.transport.rent.RequestForRent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.jme.shareride.service.vehicleServices.VehicleServiceImpl.mapVehicleToDetails;


@Service
@AllArgsConstructor
public class RentServiceImpl implements RentService{

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final DeliveryInfoRepository deliveryInfoRepository;
    private final RentRepository rentRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public ResponseEntity confirmBooking(HttpServletRequest request, RequestForRent requestForRent) {
        Rent rent = new Rent();
        Vehicle vehicle = vehicleRepository.findById(requestForRent.getVehicleId()).get();
         UserEntity user = extractUser(request);

         Rent existingRent = rentRepository.findByCustomer(user);
         if(existingRent != null){
             rentRepository.delete(existingRent);
             DeliveryInfo deliveryInfo = deliveryInfoRepository.findByUser(user);
             String picOffLocation = deliveryInfo.getPicUpLocation();
             String dropOffLocation = deliveryInfo.getDropOffLocation();
             String distance = deliveryInfo.getDistanceInString();
             String duration = deliveryInfo.getDurationInString();

             rent.setDistanceInInt(deliveryInfo.getDistanceInInt());
             rent.setDurationInInt(deliveryInfo.getDurationInInt());
             rent.setPickOffLocation(picOffLocation);
             rent.setDropOffLocation(dropOffLocation);
             rent.setDistanceInString(distance);
             rent.setDurationInString(duration);
             rent.setTime(requestForRent.getTime());
             rent.setDate(requestForRent.getDate());
             rent.setVehicle(vehicle);
             rent.setCustomer(user);

             rentRepository.save(rent);
             deliveryInfoRepository.delete(deliveryInfo);

             Rent savedRent = rentRepository.findByCustomer(user);
             return ResponseHandler.handle(201, "Proceed to Order",mapRentToDto(savedRent));
         }
        DeliveryInfo deliveryInfo = deliveryInfoRepository.findByUser(user);
        String picOffLocation = deliveryInfo.getPicUpLocation();
        String dropOffLocation = deliveryInfo.getDropOffLocation();
        String distance = deliveryInfo.getDistanceInString();
        String duration = deliveryInfo.getDurationInString();

        rent.setDistanceInInt(deliveryInfo.getDistanceInInt());
        rent.setDurationInInt(deliveryInfo.getDurationInInt());
        rent.setPickOffLocation(picOffLocation);
        rent.setDropOffLocation(dropOffLocation);
        rent.setDistanceInString(distance);
        rent.setDurationInString(duration);
        rent.setTime(requestForRent.getTime());
        rent.setDate(requestForRent.getDate());
        rent.setVehicle(vehicle);
        rent.setCustomer(user);

        rentRepository.save(rent);
        deliveryInfoRepository.delete(deliveryInfo);

        Rent savedRent = rentRepository.findByCustomer(user);

        return ResponseHandler.handle(201, "Proceed to Order",mapRentToDto(savedRent));
    }



    public UserEntity extractUser(HttpServletRequest servletRequest) {
        String authHeader = servletRequest.getHeader("Authorization");
        if (authHeader == null) {
            throw new UsernameNotFoundException("User must be logged In");
        }
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        UserEntity user = userRepository.findByEmailOrPhoneNumber(username, username);
        return user;
    }

    RentDto mapRentToDto(Rent rent){
        RentDto rentDto = RentDto.builder()
                .id(rent.getId())
                .vehicleDetails(mapVehicleToDetails(rent.getVehicle()))
                .date(rent.getDate())
                .time(rent.getTime())
                .pickOffLocation(rent.getPickOffLocation())
                .dropOffLocation(rent.getDropOffLocation())
                .username(rent.getCustomer().getUsername())
                .distance(rent.getDistanceInString())
                .duration(rent.getDurationInString())
                .build();

        return rentDto;
    }

}

