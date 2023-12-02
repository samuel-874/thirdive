package com.jme.shareride.service.chatservices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.user_and_auth.chat.Chat;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements  ChatService{
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    @Autowired
    public ChatServiceImpl(JwtService jwtService, UserRepository userRepository, ChatRepository chatRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public ResponseEntity createChat(
            HttpServletRequest servletRequest,
            long recipientId
    ) {
        UserEntity sender = extractUser(servletRequest);
        UserEntity recipient = userRepository.findById(recipientId).get();

        Chat chat = new Chat();
        chat.setCustomer(sender);
        chat.setDriver(recipient);
        return ResponseHandler.handle(201, "Chat has been create", chat);
    }


    public UserEntity extractUser(
            HttpServletRequest servletRequest
    ) {
        String authHeader = servletRequest.getHeader("Authorization");
        if (authHeader == null)
        {
            throw new UsernameNotFoundException("User must be logged In");
        }

        String jwt = authHeader.substring(7);

        String username = jwtService.extractUsername(jwt);
        UserEntity user =
                userRepository.findByEmailOrPhoneNumber(username, username);
        return user;
    }

}
