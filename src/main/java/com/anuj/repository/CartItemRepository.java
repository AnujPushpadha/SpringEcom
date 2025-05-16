package com.anuj.repository;

import com.anuj.model.Cart;
import com.anuj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anuj.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);


}
