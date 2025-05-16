package com.anuj.service;

//import com.anuj.exception.CartItemException;
//import com.anuj.exception.UserException;
import com.anuj.model.CartItem;


public interface CartItemService {

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception ;

    public void removeCartItem(Long userId, Long cartItemId)  throws Exception;

    public CartItem findCartItemById(Long cartItemId) throws Exception;
}