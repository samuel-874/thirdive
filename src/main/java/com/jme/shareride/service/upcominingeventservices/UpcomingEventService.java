package com.jme.shareride.service.upcominingeventservices;

import com.jme.shareride.requests.transport.rent.BookLaterRequest;
import com.jme.shareride.requests.transport.rent.RequestForRent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UpcomingEventService {
    ResponseEntity bookLater(HttpServletRequest request, BookLaterRequest requestForRent);
    ResponseEntity allUpcomingEvents(HttpServletRequest servletRequest);
    ResponseEntity eventDetails(long eventId);

}
