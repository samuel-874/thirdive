package com.jme.shareride.service.OrderServices;

import com.jme.shareride.entity.transport.Order;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByCustomer(UserEntity customer);
}
