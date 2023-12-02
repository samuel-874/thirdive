package com.jme.shareride.service.transactionservices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.user_and_auth.Transaction;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository repository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Override
    public ResponseEntity findAllUsersTransaction(
            HttpServletRequest httpServletRequest
    ) {
        UserEntity user = extractUser(httpServletRequest);

        List<Transaction> allTransactions = repository.findByUser(user);
        return ResponseHandler.handle(200, "All " + user.getUsername() +" transactions",allTransactions);
    }

    @Override
    public ResponseEntity transactionDetails(long id) {
        Transaction transaction = repository.findById(id).get();
        return ResponseHandler.handle(200, "Transaction retrieved successfully",transaction);
    }


    private UserEntity extractUser(
            HttpServletRequest httpServletRequest
    ){
        String authHeader = httpServletRequest.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String email = jwtService.extractUsername(jwt);

        UserEntity user = userRepository.findByEmail(email);
        return user;
    }
}
