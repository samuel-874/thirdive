//package com.jme.shareride.entity.transport;
//
//import com.jme.shareride.entity.enums.Gear;
//import com.jme.shareride.entity.user_and_auth.Location;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Builder
//public class Product {
//    @SequenceGenerator(
//            name = "product_sequence",
//            sequenceName = "product_sequence",
//            allocationSize = 1
//    )
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_sequence")
//    private long id;
//    private String productName;
//    private String photoUrl;
//    @ManyToOne
//    private Category category;
//    private String productSeatCount;
//    private String fuelType;
//    @Enumerated(EnumType.STRING)
//    private Gear gearTypes;
//
//    @OneToMany
//    private List<Vehicle> vehicles;
//}
