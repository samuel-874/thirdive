package com.jme.shareride.service.cancelledservices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CancelService {
    ResponseEntity cancelOrder(HttpServletRequest servletRequest);

    ResponseEntity findAllCancelledOrders(HttpServletRequest servletRequest);

    ResponseEntity cancelOrder(long id);
}
