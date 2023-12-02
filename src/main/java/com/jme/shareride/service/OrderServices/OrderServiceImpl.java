package com.jme.shareride.service.OrderServices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.dto.OrderDto;
import com.jme.shareride.entity.transport.Order;
import com.jme.shareride.entity.transport.Rent;
import com.jme.shareride.entity.transport.Vehicle;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.rentservices.RentRepository;
import com.jme.shareride.service.userServices.UserRepository;
import com.jme.shareride.service.vehicleServices.VehicleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.jme.shareride.service.vehicleServices.VehicleServiceImpl.mapVDtoToDisplayObject;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final VehicleRepository vehicleRepository;
    private final RentRepository rentRepository;



    @Override
    public ResponseEntity confirmRide(
            HttpServletRequest request,
            int  vat
    ) {

        UserEntity customer = extractUser(request);
        Rent rent = rentRepository.findByCustomer(customer);
        Order existingOrder = orderRepository.findByCustomer(customer);
        if(existingOrder != null){
            orderRepository.delete(existingOrder);
            Order order = mapRentToOrder(rent);
            Vehicle vehicle = rent.getVehicle();
            int charge = vehicle.getChargePerHour();
            double subTotal = (double) charge   * ((double)rent.getDurationInInt() /3600);
            double valueAddedTax =  subTotal * ((double) vat/100);
            double total = subTotal + valueAddedTax;            order.setCharge(charge);
            order.setVat(vat);
            order.setTotal((int) total);
            order.setCustomer(customer);

            orderRepository.save(order);
            rentRepository.delete(rent);

            Order savedOrder = orderRepository.findByCustomer(customer);
            return ResponseHandler.handle(201, "Order has been placed", mapOrderToDto(savedOrder));
        }

        Order order = mapRentToOrder(rent);
        Vehicle vehicle = rent.getVehicle();
        int charge = vehicle.getChargePerHour();
        double subTotal = (double) charge   * ((double)rent.getDurationInInt() /3600);
        double valueAddedTax =  subTotal * ((double) vat/100);
        double total = subTotal + valueAddedTax;
        order.setCharge(charge);
        order.setVat(vat);
        order.setTotal((int) total);
        order.setCustomer(customer);
        orderRepository.save(order);
        rentRepository.delete(rent);

        Order savedOrder = orderRepository.findByCustomer(customer);

     return ResponseHandler.handle(201,
             "Order has been placed",
             mapOrderToDto(savedOrder));
    }





    public Order mapRentToOrder(Rent rent){
        Order order = Order.builder()
                .orderedVehicle(rent.getVehicle())
                .pickOffLocation(rent.getPickOffLocation())
                .dropOffLocation(rent.getDropOffLocation())
                .distance(rent.getDistanceInString())
                .duration(rent.getDurationInString())
                .date(rent.getDate())
                .time(rent.getTime())
                .build();
        return order;
    }


    public UserEntity extractUser(
            HttpServletRequest servletRequest
    ) {
        String authHeader = servletRequest.getHeader("Authorization");
        if (authHeader == null) {
            throw new UsernameNotFoundException("User must be logged In");
        }
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        UserEntity user = userRepository
                .findByEmailOrPhoneNumber(username, username);
        return user;
    }



   public static OrderDto mapOrderToDto(Order order){
        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .vehicle(mapVDtoToDisplayObject(order.getOrderedVehicle()))
                .pickOffLocation(order.getPickOffLocation())
                .dropOffLocation(order.getDropOffLocation())
                .distance(order.getDistance())
                .duration(order.getDuration())
                .date(order.getDate())
                .time(order.getTime())
                .charge(order.getCharge())
                .vat(order.getVat())
                .total(order.getTotal())
                .customersName(order.getCustomer().getUsername())
                .build();

        return orderDto;
    }

}
