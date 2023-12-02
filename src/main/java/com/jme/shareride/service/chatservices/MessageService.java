package com.jme.shareride.service.chatservices;

import com.jme.shareride.requests.chat.ChatRequest;
import com.jme.shareride.requests.contactus.ContactSupportRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface MessageService {

    ResponseEntity sendMessage(ChatRequest chatRequest, HttpServletRequest request);
    ResponseEntity contactSupport(ContactSupportRequest contactSupportRequest) throws MessagingException, UnsupportedEncodingException;


}
