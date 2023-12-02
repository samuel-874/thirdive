package com.jme.shareride.dto;

import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class NotificationDto {
    private long id;
    private String message;
    private LocalDateTime sentAt;

}
