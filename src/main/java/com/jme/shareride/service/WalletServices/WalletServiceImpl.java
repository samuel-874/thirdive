package com.jme.shareride.service.WalletServices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.entity.user_and_auth.Wallet;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private JwtService jwtService;

    @Override
    public ResponseEntity getUsersWallet(
            HttpServletRequest httpServletRequest
    ) {
        UserEntity user = extractUser(httpServletRequest);
        Wallet wallet = walletRepository.findByUser(user).orElseGet(()-> initializeWallet(user));
        return ResponseHandler.handle(
                200,
                user.getUsername() +"'s wallet",
                wallet);
    }

    @Override
    public ResponseEntity addMoney(
            HttpServletRequest servletRequest,
            int amount
    ) {
        UserEntity user = extractUser(servletRequest);
        Wallet wallet = walletRepository.findByUser(user).orElseGet(()-> initializeWallet(user));
        int newBalance = wallet.getBalance() + amount;
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
        return ResponseHandler.handle(200, amount + " has been added to wallet", wallet);
    }

    @Override
    public ResponseEntity deductMoney(
            HttpServletRequest servletRequest,
            int amount
    ) {
        UserEntity user = extractUser(servletRequest);
        Wallet wallet = walletRepository.findByUser(user).get();
        int newBalance = wallet.getBalance() - amount;
        int totalExpense = wallet.getTotalExpend() + amount;
        if(newBalance < amount){
            return ResponseHandler.handle(
                    403,
                    "InSufficient fund",
                    null);
        }
            wallet.setBalance(newBalance);
            walletRepository.save(wallet);
            return ResponseHandler.handle(
                    200,
                    "Payment successful",
                    wallet);
    }

    @Override
    public int getBalance(HttpServletRequest servletRequest) {
        Wallet usersWallet = walletRepository.findByUser(extractUser(servletRequest)).get();
        int balance = usersWallet.getBalance();
        return balance;
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

    private Wallet initializeWallet(
            UserEntity user
    ){
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0);
        return walletRepository.save(wallet);
    }
}
