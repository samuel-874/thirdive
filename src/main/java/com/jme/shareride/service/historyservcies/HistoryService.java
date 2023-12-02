package com.jme.shareride.service.historyservcies;

import com.jme.shareride.entity.transport.History;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface HistoryService {
    History getOrCreateHistory(UserEntity user);

    ResponseEntity viewHistory(HttpServletRequest servletRequest);
}
