package com.jme.shareride.service.upcominingeventservices;

import com.jme.shareride.dto.UpcomingEventsDto;
import com.jme.shareride.entity.transport.UpcomingEvent;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UpcomingEventRepository extends JpaRepository<UpcomingEvent,Long> {

    List<UpcomingEvent> findByCustomer(UserEntity user);
}
