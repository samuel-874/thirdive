package com.jme.shareride.service.upcominingeventservices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.dto.UpcomingEventsDto;
import com.jme.shareride.entity.transport.DeliveryInfo;
import com.jme.shareride.entity.transport.History;
import com.jme.shareride.entity.transport.UpcomingEvent;
import com.jme.shareride.entity.transport.Vehicle;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.requests.transport.rent.BookLaterRequest;
import com.jme.shareride.service.devileryinfoservices.DeliveryInfoRepository;
import com.jme.shareride.service.historyservcies.HistoryService;
import com.jme.shareride.service.userServices.UserRepository;
import com.jme.shareride.service.vehicleServices.VehicleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.jme.shareride.service.vehicleServices.VehicleServiceImpl.mapVehicleToDetails;


@Service
@AllArgsConstructor
public class UpcomingEventServiceImpl implements UpcomingEventService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final HistoryService historyService;
    private final DeliveryInfoRepository deliveryInfoRepository;
    private final UpcomingEventRepository eventRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public ResponseEntity bookLater(HttpServletRequest request, BookLaterRequest requestForRent) {
        UpcomingEvent upcomingEvent = new UpcomingEvent();

        Vehicle vehicle = vehicleRepository.findById(requestForRent.getVehicleId()).get();
         UserEntity user = extractUser(request);
        History history = historyService.getOrCreateHistory(user);

        DeliveryInfo deliveryInfo = deliveryInfoRepository.findByUser(user);
        String picOffLocation = deliveryInfo.getPicUpLocation();
        String dropOffLocation = deliveryInfo.getDropOffLocation();
        String distance = deliveryInfo.getDistanceInString();
        String duration = deliveryInfo.getDurationInString();

        upcomingEvent.setVehicle(vehicle);
        upcomingEvent.setPickOffLocation(picOffLocation);
        upcomingEvent.setDropOffLocation(dropOffLocation);
        upcomingEvent.setDistanceInString(distance);
        upcomingEvent.setDurationInString(duration);
        upcomingEvent.setDistanceInInt(deliveryInfo.getDistanceInInt());
        upcomingEvent.setDurationInInt(deliveryInfo.getDurationInInt());
        upcomingEvent.setTime(requestForRent.getTime());
        upcomingEvent.setHistory(history);
        upcomingEvent.setDate(requestForRent.getDate());
        upcomingEvent.setCustomer(user);

        UpcomingEvent event = eventRepository.save(upcomingEvent);
        deliveryInfoRepository.delete(deliveryInfo);


        return ResponseHandler.handle(201, "Ride has been booked for later", mapUpComingEventToDto(event));
    }

    @Override
    public ResponseEntity allUpcomingEvents(HttpServletRequest servletRequest) {
        UserEntity user =extractUser(servletRequest);
        List<UpcomingEvent> savedEvent = eventRepository.findByCustomer(user);
        List<UpcomingEventsDto> allEvents = savedEvent.stream().map(events->mapUpComingEventToDto(events)).collect(Collectors.toList());
        return ResponseHandler.handle(200, "All ride available", allEvents);
    }

    @Override
    public ResponseEntity eventDetails(long eventId) {
        UpcomingEvent event = eventRepository.findById(eventId).get();
        return ResponseHandler.handle(200, "Event retrieved ", mapUpComingEventToDto(event));
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

    UpcomingEventsDto mapUpComingEventToDto(UpcomingEvent events){
        UpcomingEventsDto upcomingEventsDto = UpcomingEventsDto.builder()
                .id(events.getId())
                .vehicleDetails(mapVehicleToDetails(events.getVehicle()))
                .date(events.getDate())
                .time(events.getTime())
                .pickOffLocation(events.getPickOffLocation())
                .dropOffLocation(events.getDropOffLocation())
                .username(events.getCustomer().getUsername())
                .distance(events.getDistanceInString())
                .duration(events.getDurationInString())
                .build();

        return upcomingEventsDto;
    }

}