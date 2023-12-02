package com.jme.shareride.service.historyservcies;

import com.jme.shareride.entity.transport.History;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface HistoryRepository extends JpaRepository<History,Long> {
    Optional<History> findHistoryByUser(UserEntity user);
}
