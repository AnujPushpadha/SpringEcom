package com.anuj.service.impl;

import com.anuj.config.JwtProvider;
import com.anuj.model.User;
import com.anuj.repository.UserRepository;
import com.anuj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);

        User user=this.findUserByEmail(email);
//
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("user not found"+email);
        }
        return user;
    }
}
