package com.jme.shareride.service.review;

import com.jme.shareride.entity.user_and_auth.review.Review;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ReviewRepository extends JpaRepository<Review ,Long>{
    List<Review> findByDriver(UserEntity driver);
}
