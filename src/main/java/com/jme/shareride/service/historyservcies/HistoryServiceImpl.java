package com.jme.shareride.service.historyservcies;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.transport.History;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService{
    private final HistoryRepository historyRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public History getOrCreateHistory(UserEntity user){
        History history = historyRepository.findHistoryByUser(user).orElseGet(()->createHistory(user));
        return history;
    }

    @Override
    public ResponseEntity viewHistory(
            HttpServletRequest servletRequest
    ) {
        UserEntity user = extractUser(servletRequest);
        History history = historyRepository.findHistoryByUser(user).get();
        if(history == null){
            createHistory(user);
        }
        return ResponseHandler.handle(200, "All users history", history);
    }

    public History createHistory(UserEntity user){
        History history = new History();
        history.setUser(user);
        historyRepository.save(history);
        return history;
    }
    public UserEntity extractUser(HttpServletRequest servletRequest) {
        String authHeader = servletRequest.getHeader("Authorization");
        if (authHeader == null) {
            throw new UsernameNotFoundException("User must be logged In");
        }
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        UserEntity user = userRepository.findByEmailOrPhoneNumber(username, username);
        return user;
    }
}
