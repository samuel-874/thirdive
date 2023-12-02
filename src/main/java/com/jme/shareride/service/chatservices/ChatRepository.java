package com.jme.shareride.service.chatservices;

import com.jme.shareride.entity.user_and_auth.chat.Chat;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    Optional<Chat> findChatByDriverAndCustomer(UserEntity driver, UserEntity customer);

    Optional<Chat> findByCustomerAndDriver(UserEntity customer, UserEntity driver);
}
