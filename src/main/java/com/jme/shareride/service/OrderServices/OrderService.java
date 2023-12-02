package com.jme.shareride.service.OrderServices;

import com.jme.shareride.requests.transport.order.OrderRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    ResponseEntity confirmRide(HttpServletRequest request, int vat);
}
