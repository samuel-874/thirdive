package com.jme.shareride.entity.user_and_auth.review;

import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "complains")
@AllArgsConstructor
@NoArgsConstructor
public class Complain {
    @Id
    @SequenceGenerator(
            name = "complain_sequence",
            sequenceName = "complain_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "complain_sequence")
    private long id;
    @OneToOne
    private UserEntity user;
    @OneToOne
    private UserEntity driver;
    private String subject;
    private String content;
}
