package com.jme.shareride.service.cancelledservices;

import com.jme.shareride.entity.transport.Cancelled;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CancelRepository extends JpaRepository<Cancelled,Long> {
    List<Cancelled> findByCustomer(UserEntity user);
}
