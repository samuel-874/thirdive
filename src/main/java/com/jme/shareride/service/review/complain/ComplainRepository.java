package com.jme.shareride.service.review.complain;

import com.jme.shareride.entity.user_and_auth.review.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@EnableJpaRepositories
public interface ComplainRepository extends JpaRepository<Complain,Long> {
}
