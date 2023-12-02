package com.jme.shareride.service.WalletServices;

import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.entity.user_and_auth.Wallet;
import com.jme.shareride.entity.user_and_auth.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByUser(UserEntity user);
}