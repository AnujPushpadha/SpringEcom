package com.anuj.service;

import com.anuj.exception.ProductException;
import com.anuj.model.Cart;
import com.anuj.model.CartItem;
import com.anuj.model.Product;
import com.anuj.model.User;
//import com.anuj.request.AddItemRequest;

public interface CartService {

    public CartItem addCartItem(User user,
                                Product product,
                                String size,
                                int quantity) throws ProductException;

    public Cart findUserCart(User user);
}