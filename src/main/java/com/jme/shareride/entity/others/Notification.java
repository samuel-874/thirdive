package com.jme.shareride.entity.others;

import com.jme.shareride.entity.enums.Status;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    @SequenceGenerator(
            name = "notification_sequence",
            sequenceName = "notification_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "notification_sequence")
    private long id;
    private String message;
    private LocalDateTime sentAt;
    @OneToOne(cascade = CascadeType.REMOVE)
    private UserEntity receiver;
    @Enumerated(EnumType.STRING)
    private Status status;

}