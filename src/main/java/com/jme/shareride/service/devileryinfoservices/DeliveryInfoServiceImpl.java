package com.jme.shareride.service.devileryinfoservices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.transport.DeliveryInfo;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.google.service.DistanceCalculationService;
import com.jme.shareride.external.google.service.model.Element;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.userServices.UserRepository;
import com.jme.shareride.requests.transport.others.DeliveryInfoRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeliveryInfoServiceImpl implements DeliveryInfoService{
    @Autowired
    private JwtService jwtservice;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;
    @Autowired
    private DistanceCalculationService calculationService;

    @Override
    public ResponseEntity saveInfo(
            DeliveryInfoRequest infoRequest,
            HttpServletRequest request
    ) {
        DeliveryInfo deliveryInfo =
                mapDeliveryRequestToObject(infoRequest);
            UserEntity user = extractUser(request);

        deliveryInfo.setUser(user);
        if(user == null){
            return ResponseHandler.handle(
                    401,
                    "User must be login",
                    null);
        }
            Element element =calculationService.calculate_location(
                    infoRequest.getFrom(), infoRequest.getTo());
            String distance = element.getDistance().getText();
            String distanceInt = element.getDistance().getValue();
//            int distanceInMeters = Integer.valueOf(distanceInt) * 1000;
            String duration = element.getDuration().getText();
        deliveryInfo.setDurationInString(duration);
        deliveryInfo.setDistanceInString(distance);


        deliveryInfo.setDurationInInt(Integer.valueOf(element.getDuration().getValue()));
        deliveryInfo.setDistanceInInt(Integer.valueOf(element.getDistance().getValue()));

        DeliveryInfo savedInfo = deliveryInfoRepository.save(deliveryInfo);

        return ResponseHandler.handle(201, "Location has been confirmed ", savedInfo);
    }


    DeliveryInfo mapDeliveryRequestToObject(DeliveryInfoRequest infoRequest){
        DeliveryInfo deliveryInfo = DeliveryInfo.builder()
                .picUpLocation(infoRequest.getFrom())
                .dropOffLocation(infoRequest.getTo())
                .build();
        return deliveryInfo;
    }

    public  UserEntity extractUser(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);

        String userEmail = jwtservice.extractUsername(jwt);
        UserEntity user = userRepository.findByEmail(userEmail);
        if(user == null){
            throw  new UsernameNotFoundException("No user must be logged in");
        }

        return  user;
    }
}
