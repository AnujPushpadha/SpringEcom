package com.anuj.controller;

import com.anuj.domain.USER_ROLE;
import com.anuj.modal.User;
import com.anuj.modal.VerificationCode;
import com.anuj.repository.UserRepository;
import com.anuj.request.LoginOtpRequest;
import com.anuj.request.LoginRequest;
import com.anuj.responce.ApiResponce;
import com.anuj.responce.AuthResponse;
import com.anuj.responce.SignupRequest;
import com.anuj.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final UserRepository userRepository;
    private final AuthService authService;
//    @RequiredArgsConstructor give blow code
//    public AuthController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @PostMapping("/signup")
//    public ResponseEntity<User>  createUserHandler(@RequestBody SignupRequest req ){

//       User user=new User();
//       user.setEmail(req.getEmail());
//       user.setFullName(req.getFullName());
//
//       User savedUser=userRepository.save(user);
//               return ResponseEntity.ok(savedUser);

    public ResponseEntity<AuthResponse>  createUserHandler(@RequestBody SignupRequest req ) throws Exception {
        String jwt =authService.createUser(req);

        AuthResponse res=new AuthResponse();

        res.setJwt(jwt);
        res.setMessage("success");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(res);
    }


    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponce> sentLoginOtp(
            @RequestBody VerificationCode req) throws Exception {
//            @RequestBody LoginOtpRequest req) throws Exception {
        authService.sentLoginOtp(req.getEmail());
//        authService.sentLoginOtp(req.getEmail(),req.getRole());

          ApiResponce res =new ApiResponce();

        res.setMessage("otp sent");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest) throws Exception {


        AuthResponse authResponse = authService.signing(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
