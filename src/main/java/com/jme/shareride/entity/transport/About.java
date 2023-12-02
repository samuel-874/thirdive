package com.jme.shareride.entity.transport;

import com.jme.shareride.entity.enums.Gear;
import com.jme.shareride.entity.transport.Vehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "abouts")
public class About {
    @SequenceGenerator(
            name = "abouts_sequence",
            sequenceName = "abouts_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "about_sequence")
    private long id;
    private String maxPower;
    private String fuelDurability;
    private String maxSpeed;
    private String mph;
    private String color;
    private String fuelType;
    @Enumerated(EnumType.STRING)
    private Gear gearType;
    private int seatCount;

    @OneToOne(mappedBy = "about",cascade = CascadeType.REMOVE)
    private Vehicle vehicle;
}
