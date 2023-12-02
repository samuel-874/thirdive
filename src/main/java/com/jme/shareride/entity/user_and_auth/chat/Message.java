package com.jme.shareride.entity.user_and_auth.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;




@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message {
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy =
            GenerationType.SEQUENCE,
            generator = "message_sequence"
    )
    private long id;
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private UserEntity sender;
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE
    )
    private UserEntity recipient;
    private LocalDateTime time;
    private String content;
    @ManyToOne(
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(name = "chats_id")
    @JsonIgnore
    private Chat chat;
}
