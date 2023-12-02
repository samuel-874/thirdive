package com.jme.shareride.service.vehicleServices;

import com.jme.shareride.entity.transport.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface AboutRepository extends JpaRepository<About,Long> {
}
