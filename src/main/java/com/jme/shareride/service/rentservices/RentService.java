package com.jme.shareride.service.rentservices;

import com.jme.shareride.dto.RentDto;
import com.jme.shareride.requests.transport.rent.RequestForRent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RentService {
    ResponseEntity confirmBooking(HttpServletRequest request, RequestForRent requestForRent);
}
