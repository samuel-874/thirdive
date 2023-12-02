package com.jme.shareride.service.transactionservices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    ResponseEntity findAllUsersTransaction(HttpServletRequest httpServletRequest);
    ResponseEntity transactionDetails(long id);

}
