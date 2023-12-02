package com.jme.shareride.external.twillio;

import com.jme.shareride.entity.user_and_auth.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface OtpRepository extends JpaRepository<OtpEntity,Long> {
    OtpEntity findByOwnersNumber(String phoneNumber);
}
