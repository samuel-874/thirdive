package com.jme.shareride.service.transactionservices;

import com.jme.shareride.entity.user_and_auth.Transaction;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByUser(UserEntity user);
}
