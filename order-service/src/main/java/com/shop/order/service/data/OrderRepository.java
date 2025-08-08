package com.shop.order.service.data;

import com.shop.order.service.data.dataClases.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long  userId);
}
