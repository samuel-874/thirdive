package com.jme.shareride.service.rentservices;

import com.jme.shareride.entity.transport.Rent;
import com.jme.shareride.entity.transport.Vehicle;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface RentRepository extends JpaRepository<Rent,Long> {
    Rent findByCustomer(UserEntity user);
}
