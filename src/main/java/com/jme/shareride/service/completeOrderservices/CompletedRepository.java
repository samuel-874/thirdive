package com.jme.shareride.service.completeOrderservices;

import com.jme.shareride.entity.transport.CompletedOrder;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CompletedRepository extends JpaRepository<CompletedOrder,Long> {
    List<CompletedOrder> findByCustomer(UserEntity user);
}
