package com.anuj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuj.modal.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long userId);
}
