package com.jme.shareride.entity.transport;

import com.jme.shareride.entity.others.ImageData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @SequenceGenerator(
            name = "categories_sequence",
            sequenceName = "categories_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_sequence")
    private long id;
    private String categoryName;
    @OneToOne(cascade = CascadeType.REMOVE)
    private ImageData image;
    @OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE)
    private List<Vehicle> vehicleList;

}
