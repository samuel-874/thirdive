package com.jme.shareride.service.completeOrderservices;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CompletedService {

    ResponseEntity completeOrder(HttpServletRequest servletRequest);
    ResponseEntity findAllUsersCompletedOrders(HttpServletRequest servletRequest);
    ResponseEntity completedOrderDetails(long id);
}
