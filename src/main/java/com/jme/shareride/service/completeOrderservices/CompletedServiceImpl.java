package com.jme.shareride.service.completeOrderservices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.transport.CompletedOrder;
import com.jme.shareride.entity.transport.History;
import com.jme.shareride.entity.transport.Order;
import com.jme.shareride.entity.user_and_auth.Transaction;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.entity.user_and_auth.Wallet;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.OrderServices.OrderRepository;
import com.jme.shareride.service.WalletServices.WalletRepository;
import com.jme.shareride.service.WalletServices.WalletService;
import com.jme.shareride.service.historyservcies.HistoryService;
import com.jme.shareride.service.transactionservices.TransactionRepository;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedServiceImpl implements CompletedService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final HistoryService historyService;
    private final WalletService walletService;
    private final CompletedRepository completedRepository;

    @Autowired
    public CompletedServiceImpl(
            JwtService jwtService,
            UserRepository userRepository,
            OrderRepository orderRepository,
            WalletRepository walletRepository, TransactionRepository transactionRepository, HistoryService historyService,
            WalletService walletService, CompletedRepository completedRepository
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.historyService = historyService;
        this.walletService = walletService;

        this.completedRepository = completedRepository;
    }


    @Override
    public ResponseEntity completeOrder(HttpServletRequest servletRequest){
        UserEntity user = extractUser(servletRequest);
        History history = historyService.getOrCreateHistory(user);
        Order order = orderRepository.findByCustomer(user);
        CompletedOrder completedOrder = CompletedOrder.builder()
                .orderedVehicle(order.getOrderedVehicle())
                .pickOffLocation(order.getPickOffLocation())
                .dropOffLocation(order.getDropOffLocation())
                .distance(order.getDistance())
                .duration(order.getDuration())
                .date(order.getDate())
                .history(history)
                .time(order.getTime())
                .customer(user)
                .charge(order.getCharge())
                .vat(order.getVat())
                .total(order.getTotal())
                .build();

        Wallet wallet = walletRepository.findByUser(user).get();
        int currentBalance = wallet.getBalance();
        walletService.deductMoney(servletRequest, order.getTotal());

        Transaction transaction = new Transaction();
        transaction.setSubTotal(order.getCharge());
        transaction.setTotal(order.getTotal());
        transaction.setUser(user);
        transaction.setWallet(wallet);
        transaction.setVat(order.getVat());
        wallet.getTransactions().add(transaction);


        completedRepository.save(completedOrder);
        transactionRepository.save(transaction);
        walletRepository.save(wallet);
        orderRepository.delete(order);

        return ResponseHandler.handle(201, "Order has been completed", completedOrder);
    }

    @Override
    public ResponseEntity findAllUsersCompletedOrders(HttpServletRequest servletRequest) {
        UserEntity user = extractUser(servletRequest);
        List<CompletedOrder> allUserOrdered = completedRepository.findByCustomer(user);
        return ResponseHandler.handle(200, "all completed orders for "+ user.getUsername(), allUserOrdered);
    }


    @Override
    public ResponseEntity completedOrderDetails(long id) {
        CompletedOrder completedOrder = completedRepository.findById(id).get();
        return ResponseHandler.handle(200, "CompletedOrder ordered retrieved successfully", completedOrder);
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
