package com.jme.shareride.service.notificationServices;

import com.jme.shareride.requests.transport.others.NotificationRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    ResponseEntity sendNotification(NotificationRequest notificationRequest);
      ResponseEntity sendGeneral(NotificationRequest notificationRequest);

      ResponseEntity getNotification(HttpServletRequest httpServletRequest);


}
