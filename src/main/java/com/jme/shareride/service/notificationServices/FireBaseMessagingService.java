package com.jme.shareride.service.notificationServices;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.jme.shareride.requests.transport.others.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireBaseMessagingService {
    @Autowired
   private  FirebaseMessaging firebaseMessaging;


    public String sendNotificationByToken(NotificationRequest request,String token){
        Notification notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getMessage())
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(token)
                .build();

        try{
            firebaseMessaging.send(message);
            return "Message sent successfully";
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
            return "Error occurred in an Attempt to send Notification";
        }
 }

}