package com.jme.shareride.entity.user_and_auth.review;

import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ratings")
public class Rating {
    @SequenceGenerator(
            name = "rating_sequence",
            sequenceName = "rating_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "rating_sequence")
    private long id;
    @OneToOne
    private UserEntity driver;
    private int fiveStars =0;
    private int fourStars =0;
    private int threeStars =0;
    private int twoStars =0;
    private int oneStar =0;
    private int averageRate =0;




}
