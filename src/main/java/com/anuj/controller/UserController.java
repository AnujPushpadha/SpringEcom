package com.anuj.controller;

import com.anuj.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.anuj.exception.UserException;

import com.anuj.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> createUserHandler(
            @RequestHeader("Authorization") String jwt) throws Exception {

        System.out.println("/api/users/profile");
        User user = userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(user);
    }
}
