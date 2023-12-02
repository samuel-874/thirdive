package com.jme.shareride.service.locationservices;

import com.jme.shareride.entity.user_and_auth.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface LocationRepository extends JpaRepository<Location,Long> {
}
