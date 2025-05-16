package com.anuj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuj.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long userId);
}
