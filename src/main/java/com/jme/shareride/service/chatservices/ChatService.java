package com.jme.shareride.service.chatservices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {
    ResponseEntity createChat(HttpServletRequest servletRequest, long recipientId);
}
