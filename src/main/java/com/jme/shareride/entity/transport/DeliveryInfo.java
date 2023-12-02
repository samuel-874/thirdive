package com.jme.shareride.entity.transport;

import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DeliveryInfo {
    @SequenceGenerator(
            name = "delivery_info_seq",
            sequenceName = "delivery_info_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "delivery_info_seq")
    private long id;
    private String picUpLocation;
    private String dropOffLocation;
    @OneToOne
    private UserEntity user;
    private String distanceInString;
    private int distanceInInt;
    private String durationInString;
    private int durationInInt;
}
