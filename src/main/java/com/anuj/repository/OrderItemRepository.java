package com.anuj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuj.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
