package com.jme.shareride.service.review;

import com.jme.shareride.entity.user_and_auth.review.Rating;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface RatingRepository extends JpaRepository<Rating,Long> {

    Optional<Rating> findByDriver(UserEntity driver);


}
