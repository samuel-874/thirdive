package com.jme.shareride.service.notificationServices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.enums.Status;
import com.jme.shareride.entity.others.Notification;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.userServices.UserRepository;
import com.jme.shareride.requests.transport.others.NotificationRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final FireBaseMessagingService messagingService;
    private final NotificationRepository notificationRepository;
    @Override
    public ResponseEntity sendNotification(NotificationRequest notificationRequest) {
        UserEntity receiver = userRepository.findByUsername(notificationRequest.getUsername());
        String token = receiver.getDeviceToken();

        Notification notification =Notification.builder()
                .message(notificationRequest.getMessage())
                .sentAt(LocalDateTime.now())
                .receiver(receiver)
                .build();
    notificationRepository.save(notification);
    messagingService.sendNotificationByToken(notificationRequest, token);
        return ResponseHandler.handle(201, "Notification has been sent to " + receiver.getUsername(), null);
    }

    @Override
    public ResponseEntity sendGeneral(NotificationRequest notificationRequest) {
        List<UserEntity> allUsers = userRepository.findAll();
        for(UserEntity user : allUsers){
            String token = user.getDeviceToken();

            Notification notification =Notification.builder()
                    .message(notificationRequest.getMessage())
                    .sentAt(LocalDateTime.now())
                    .status(Status.UNREAD)
                    .receiver(user)
                    .build();
            notificationRepository.save(notification);
            messagingService.sendNotificationByToken(notificationRequest, token);
        }

        return ResponseHandler.handle(201, "Notification has been sent to allUsers", null);
    }

    @Override
    public ResponseEntity getNotification(HttpServletRequest httpServletRequest){
        UserEntity user = getSessionUser(httpServletRequest);
        List<Notification> allNotification = notificationRepository.findByReceiver(user);

        if(allNotification == null){
            return ResponseHandler.handle(404, "Notification not found!", null);
        }
        for( Notification notification: allNotification){
            notification.setStatus(Status.READ);
            notificationRepository.save(notification);
        }

        return ResponseHandler.handle(200,"Notification fetched successfully",allNotification);
    }



    private UserEntity getSessionUser(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);

        UserEntity user = userRepository.findByEmail(userEmail);
        if(user == null){
            throw  new UsernameNotFoundException("No user must be logged in");
        }
        return user;
    }
}
