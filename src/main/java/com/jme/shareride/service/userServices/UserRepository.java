package com.jme.shareride.service.userServices;


import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByPhoneNumber(String phoneNumber);

     UserEntity findByEmail(String email);


     UserEntity findByEmailOrPhoneNumber(String emailOrPhoneNumber, String emailOrPhoneNumber1);

    UserEntity findByUsername(String vehicleOwner);
}
