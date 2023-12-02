package com.jme.shareride.entity.transport;

import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rent {
    @SequenceGenerator(
            name = "request_rent_seq",
            sequenceName = "request_rent_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "request_rent_seq")
    private long id;
    @OneToOne
    private Vehicle vehicle;
    private String pickOffLocation;
    private String dropOffLocation;
    private String distanceInString;
    private String durationInString;
    private int distanceInInt;
    private int durationInInt;
    // 2023-06-15
    private LocalDate date;
    // hh:mm:ss:msms
    private LocalTime time;
    @OneToOne
    private UserEntity customer;

}
