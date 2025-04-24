//package com.anuj.controller;
//
//import com.anuj.modal.VerificationCode;
//import com.anuj.repository.VerificationCodeRepository;
//import com.anuj.request.LoginRequest;
//import com.anuj.responce.ApiResponce;
//import com.anuj.responce.AuthResponse;
//import com.anuj.service.AuthService;
//import com.anuj.service.SellerService;
//import lombok.RequiredArgsConstructor;
//import org.apache.coyote.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/seller")
//public class SellerController {
//    private final SellerService sellerService;
//    private final VerificationCodeRepository verificationCodeRepository;
//    private final AuthService authService;
//
////    @PostMapping("/sent/login-otp")
////    public ResponseEntity<ApiResponce> sentLoginOtp(
////            @RequestBody VerificationCode req) throws Exception {
////
////        authService.sentLoginOtp(req.getEmail());
////        ApiResponce res =new ApiResponce();
////
////        res.setMessage("otp sent");
////        return new ResponseEntity<>(res, HttpStatus.CREATED);
////    }
//
//    @PostMapping("/signing")
//    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest) throws Exception {
//
//
//        AuthResponse authResponse = authService.signing(loginRequest);
//        return new ResponseEntity<>(authResponse, HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> LoginSeller(
//            @RequestBody LoginRequest req
//    ) throws Exception {
//        String otp=req.getOtp();
//        String email=req.getEmail();
//
////        VerificationCode verificationCode=verificationCodeRepository.findByEmail(email);
////        if(verificationCode==null || !verificationCode.getOtp().equals(req.getOtp())){
////           throw new Exception("wrong otp...");
////        }
//        req.setEmail("seller_"+email);
//       AuthResponse authResponse=authService.signing(req);
//        return  ResponseEntity.ok(authResponse);
//    }
//
//}
