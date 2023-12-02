package com.jme.shareride.service.notificationServices;

import com.jme.shareride.entity.others.Notification;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface NotificationRepository extends JpaRepository<Notification,Long>, PagingAndSortingRepository<Notification,Long> {
    List<Notification> findByReceiver(UserEntity user);
}
