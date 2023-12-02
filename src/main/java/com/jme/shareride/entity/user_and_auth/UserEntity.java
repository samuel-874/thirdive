package com.jme.shareride.entity.user_and_auth;

import com.jme.shareride.entity.enums.Role;
import com.jme.shareride.entity.others.ImageData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_sequence")
    private long id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role roles;
    private boolean accountEnabled;
    @OneToOne(cascade = CascadeType.REMOVE)
    private ImageData profilePics;
    private String gender;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @Enumerated(EnumType.STRING)
    private Location location;

    private String deviceId;
    private String deviceToken;
    private LocalDateTime lastLogin;

}
