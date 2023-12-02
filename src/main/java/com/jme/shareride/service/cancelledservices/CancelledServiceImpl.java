package com.jme.shareride.service.cancelledservices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.transport.Cancelled;
import com.jme.shareride.entity.transport.History;
import com.jme.shareride.entity.transport.Order;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.OrderServices.OrderRepository;
import com.jme.shareride.service.historyservcies.HistoryService;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelledServiceImpl implements CancelService{
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CancelRepository cancelRepository;
    private final HistoryService historyService;

    @Autowired
    public CancelledServiceImpl(
            JwtService jwtService,
            UserRepository userRepository,
            OrderRepository orderRepository,
            CancelRepository cancelRepository,
            HistoryService historyService
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cancelRepository = cancelRepository;
        this.historyService = historyService;
    }



    @Override
    public ResponseEntity cancelOrder(HttpServletRequest servletRequest){
        UserEntity user = extractUser(servletRequest);
        History history = historyService.getOrCreateHistory(user);
        Order order = orderRepository.findByCustomer(user);
        Cancelled cancelledOrder = Cancelled.builder()
                .orderedVehicle(order.getOrderedVehicle())
                .pickOffLocation(order.getPickOffLocation())
                .dropOffLocation(order.getDropOffLocation())
                .distance(order.getDistance())
                .duration(order.getDuration())
                .date(order.getDate())
                .time(order.getTime())
                .history(history)
                .customer(user)
                .charge(order.getCharge())
                .vat(order.getVat())
                .total(order.getTotal())
                .build();

        cancelRepository.save(cancelledOrder);
        orderRepository.delete(order);
        return ResponseHandler.handle(200, "Order has been cancelled", null);
    }


    @Override
    public ResponseEntity findAllCancelledOrders(HttpServletRequest servletRequest) {
        UserEntity user = extractUser(servletRequest);
        List<Cancelled> allCancelledOrdered = cancelRepository.findByCustomer(user);
        return ResponseHandler.handle(200, "all cancelled orders for "+ user.getUsername(), allCancelledOrdered);
    }


    @Override
    public ResponseEntity cancelOrder(long id) {
        Cancelled cancelled = cancelRepository.findById(id).get();
        return ResponseHandler.handle(200, "CompletedOrder ordered retrieved successfully", cancelled);
    }


    public UserEntity extractUser(
            HttpServletRequest httpServletRequest
    ) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader == null) {
            throw new UsernameNotFoundException("User must be logged In");
        }
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        UserEntity user = userRepository
                .findByEmailOrPhoneNumber(username, username);
        return user;
    }
}
