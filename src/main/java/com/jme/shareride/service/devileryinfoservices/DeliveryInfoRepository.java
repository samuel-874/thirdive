package com.jme.shareride.service.devileryinfoservices;

import com.jme.shareride.entity.transport.DeliveryInfo;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo,Long> {
//    DeliveryInfo findByUsername(String username);

    DeliveryInfo findByUser(UserEntity user);
}
