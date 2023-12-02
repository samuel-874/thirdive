package com.jme.shareride.service.chatservices;

import com.jme.shareride.entity.user_and_auth.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
}
