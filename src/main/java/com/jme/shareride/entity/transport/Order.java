package com.jme.shareride.entity.transport;

import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @SequenceGenerator(
            name = "orders_sequence",
            sequenceName = "orders_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "orders_sequence")
    private long id;
    @OneToOne
    private Vehicle orderedVehicle;
    private String pickOffLocation;
    private String dropOffLocation;
    private String distance;
    private String duration;
    // 2023-06-15
    private LocalDate date;
    // hh:mm:ss:msms
    private LocalTime time;
    @OneToOne
    private UserEntity customer;
    private int charge;
    private int vat;
    private int total;
}
