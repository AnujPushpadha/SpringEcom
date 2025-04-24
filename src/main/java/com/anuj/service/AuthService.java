package com.anuj.service;

import com.anuj.domain.USER_ROLE;
//import com.anuj.modal.User;
import com.anuj.request.LoginRequest;
import com.anuj.responce.AuthResponse;
import com.anuj.responce.SignupRequest;

public interface AuthService {

    String createUser(SignupRequest req) throws Exception;

//    void sentLoginOtp(String email)throws Exception;
     void sentLoginOtp(String email, USER_ROLE role)throws Exception;

    AuthResponse signing(LoginRequest req) throws Exception;

}
