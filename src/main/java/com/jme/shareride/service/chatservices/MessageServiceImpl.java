package com.jme.shareride.service.chatservices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.user_and_auth.chat.Chat;
import com.jme.shareride.entity.user_and_auth.chat.Message;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.mail.MailSenderService;
import com.jme.shareride.requests.chat.ChatRequest;
import com.jme.shareride.requests.contactus.ContactSupportRequest;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private  final JwtService jwtService;
    private final MailSenderService mailSenderService;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(ChatRepository chatRepository, UserRepository userRepository, JwtService jwtService, MailSenderService mailSenderService, MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.mailSenderService = mailSenderService;
        this.messageRepository = messageRepository;
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

    @Override
    public ResponseEntity sendMessage(
            ChatRequest chatRequest,
            HttpServletRequest request
    )
    {
        UserEntity recipient =userRepository.findById(
                        chatRequest.getDriversId()).get();
        UserEntity customer = extractUser(request);
        //checking if chat existing between user and driver so the new message can be added to it
        Chat chat = chatRepository.findChatByDriverAndCustomer(
                recipient,customer).
                orElse(chatRepository.findByCustomerAndDriver(
                        recipient,customer)
                        .orElseGet(()->createChat(recipient, customer)));
        Message message = Message.builder()
                .chat(chat)
                .time(LocalDateTime.now())
                .content(chatRequest.getContent())
                .sender(customer)
                .recipient(recipient)
                .build();
        chatRepository.save(chat);


        messageRepository.save(message);
        return ResponseHandler.handle(201, "Message sent successfully to " + recipient.getUsername(), chat);
    }

    @Override
    public ResponseEntity contactSupport(ContactSupportRequest contactSupportRequest) throws MessagingException, UnsupportedEncodingException {
        mailSenderService.sendContactEmail(contactSupportRequest);
        return ResponseHandler.handle(200, "Mail sent successfully", null);
    }


    public Chat createChat(
            UserEntity driver,
            UserEntity customer
    ) {
        Chat chat = new Chat();
                chat.setDriver(driver);
                chat.setCustomer(customer);
        return chatRepository.save(chat);
    }
}
