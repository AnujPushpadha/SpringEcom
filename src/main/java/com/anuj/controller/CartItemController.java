package com.anuj.controller;

//import com.anuj.exception.CartItemException;
//import com.anuj.exception.UserException;

import com.anuj.model.CartItem;
import com.anuj.model.User;
import com.anuj.response.ApiResponse;
import com.anuj.service.CartItemService;
import com.anuj.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    private CartItemService cartItemService;
    private UserService userService;

    public CartItemController(CartItemService cartItemService, UserService userService) {
        this.cartItemService = cartItemService;
        this.userService = userService;
    }
}