package com.anuj.service;

import com.anuj.model.User;

public interface UserService {

     User findUserByJwtToken(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
