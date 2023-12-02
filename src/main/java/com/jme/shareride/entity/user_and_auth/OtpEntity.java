package com.jme.shareride.entity.user_and_auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "otp")
public class OtpEntity {
    @SequenceGenerator(
            name = "otp_sequence",
            sequenceName = "otp_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "otp_sequence")
    private long id;
    @JoinColumn(referencedColumnName = "phoneNumber")
    private String ownersNumber;
    private LocalTime created = LocalTime.now();
    private LocalTime expires = created.plusMinutes(5);
    private String token;
}
