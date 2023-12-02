package com.jme.shareride.service.WalletServices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {
    ResponseEntity getUsersWallet(HttpServletRequest httpServletRequest);
    ResponseEntity addMoney(HttpServletRequest servletRequest,int amount);
    ResponseEntity deductMoney(HttpServletRequest servletRequest,int amount);
    int getBalance(HttpServletRequest servletRequest);

}
