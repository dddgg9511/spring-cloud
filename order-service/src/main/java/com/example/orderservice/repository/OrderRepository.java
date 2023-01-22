package com.example.orderservice.repository;

import com.example.orderservice.entity.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Orders, Long> {
    Optional<Orders> findByOrderId(String orderId);
    Iterable<Orders> findByUserId(String userId);
}
