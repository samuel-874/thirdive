package com.jme.shareride.entity.transport;

import com.jme.shareride.entity.others.ImageData;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @SequenceGenerator(
            name = "vehicles_sequence",
            sequenceName = "vehicles_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "vehicles_sequence")
     private long id;
     private String vehicleName;
     private int chargePerHour;
     @OneToOne(cascade = CascadeType.REMOVE)
     private ImageData image;
     @ManyToOne(cascade = CascadeType.REMOVE)
     private Category category;
     @OneToOne(cascade = CascadeType.REMOVE)
     private UserEntity driver;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private About about;

}
