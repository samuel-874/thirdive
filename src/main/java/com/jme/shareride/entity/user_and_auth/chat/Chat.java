package com.jme.shareride.entity.user_and_auth.chat;

import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;



@Data
@Entity
@Builder
@Table(name = "chats")
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @SequenceGenerator(
            name = "chat_sequence",
            sequenceName = "chat_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "chat_sequence")
    private long id;
    @OneToMany(mappedBy = "chat",cascade = CascadeType.REMOVE)
    private List<Message> messages;
    @ManyToOne
    private UserEntity driver;
    @ManyToOne
    private UserEntity customer;
}
