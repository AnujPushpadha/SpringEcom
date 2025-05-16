package com.anuj.service.impl;

import com.anuj.config.JwtProvider;
import com.anuj.domain.USER_ROLE;
import com.anuj.model.Cart;
import com.anuj.model.Seller;
import com.anuj.model.User;
import com.anuj.model.VerificationCode;
import com.anuj.repository.CartRepository;
import com.anuj.repository.SellerRepository;
import com.anuj.repository.UserRepository;
import com.anuj.repository.VerificationCodeRepository;
import com.anuj.request.LoginRequest;
import com.anuj.response.AuthResponse;
import com.anuj.response.SignupRequest;
import com.anuj.service.AuthService;
import com.anuj.service.EmailService;
import com.anuj.utils.OtpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserDetails;
    private final SellerRepository sellerRepository;

    @Override
    public String createUser(SignupRequest req) throws Exception {

        String email = req.getEmail();

        String fullName = req.getFullName();

        String otp = req.getOtp();

        VerificationCode verificationCode=verificationCodeRepository.findByEmail(req.getEmail());

        System.out.println("Email: " + req.getEmail());
        System.out.println("User-entered OTP: " + req.getOtp());
        System.out.println("Stored OTP: " + (verificationCode != null ? verificationCode.getOtp() : "null"));

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("wrong otp...");
        }

//        if user exist or not with email;
        User user=userRepository.findByEmail(req.getEmail());

        if(user==null){
            User createdUser=new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("7894561230");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user=userRepository.save(createdUser);

            Cart cart=new Cart();
            cart.setUser(user);
            cartRepository.save(cart);

//jwt token
            List<GrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority(
                    USER_ROLE.ROLE_CUSTOMER.toString()));


            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    req.getEmail(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtProvider.generateToken(authentication);
        }
        return "";
    }

//    @Override
//    public void sentLoginOtp(String email) throws Exception {
//
//         String SIGNING_PREFIX = "signing_";
//         //for seller
//         String SELLER_PREFIX="seller_";
//
//        if (email.startsWith(SIGNING_PREFIX)) {
//            email = email.substring(SIGNING_PREFIX.length());
//            userService.findUserByEmail(email);
//
//            User user=userRepository.findByEmail(email);
//            if(user==null){
//                throw new Exception("user not exist");
//            }
//        }
//
//        VerificationCode isExist = verificationCodeRepository
//                .findByEmail(email);
//
//        if (isExist != null) {
//            verificationCodeRepository.delete(isExist);
//        }
//
//        String otp = OtpUtils.generateOTP();
//
//        VerificationCode verificationCode = new VerificationCode();
//        verificationCode.setOtp(otp);
//        verificationCode.setEmail(email);
//        verificationCodeRepository.save(verificationCode);
//
//        System.out.println("otp: "+ otp);
//
//
//        String subject = "Ecom Login/Signup Otp";
//        String text = "your login otp is - "+otp;
//        emailService.sendVerificationOtpEmail(email, otp, subject, text);
//    }


    @Override
    public void sentLoginOtp(String email,USER_ROLE role) throws Exception {

        String SIGNING_PREFIX = "signing_";
        //for seller
//         String SELLER_PREFIX="seller_";
        System.out.println("email postman sent "+email);
        if (email.startsWith(SIGNING_PREFIX)) {
            email = email.substring(SIGNING_PREFIX.length());
//            userService.findUserByEmail(email);
            System.out.println("email header removes  "+email);
            if(role.equals(USER_ROLE.ROLE_SELLER)){

                Seller seller=sellerRepository.findByEmail(email);
                if(seller==null){
                    throw new Exception("seller not found");
                }
            }

            else{
                User user=userRepository.findByEmail(email);
                if(user==null){
                    throw new Exception("user not exist");
                }
            }

        }

        VerificationCode isExist = verificationCodeRepository
                .findByEmail(email);

        if (isExist != null) {
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtils.generateOTP();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        System.out.println("otp: "+ otp);


        String subject = "Ecom Login/Signup Otp";
        String text = "your login otp is - "+otp;
        emailService.sendVerificationOtpEmail(email, otp, subject, text);
    }
    @Override
    public AuthResponse signing(LoginRequest req) throws Exception {

        String username = req.getEmail();
        String otp = req.getOtp();

        System.out.println(username + " ----- " + otp);
//verify the otp
        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);
//generate token useing authentication
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login Success");
        authResponse.setJwt(token);
//        user roles
        System.out.println(token);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();


        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();


        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;

    }



    private Authentication authenticate(String username, String otp) throws Exception {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        String SELLER_PREFIX="seller_";
        if(username.startsWith(SELLER_PREFIX)){
            username=username.substring(SELLER_PREFIX.length());

        }

        System.out.println("sign in userDetails - " + userDetails);

        if (userDetails == null) {
            System.out.println("sign in userDetails - null ");
            throw new BadCredentialsException("Invalid username or password");
        }


        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("wrong otp...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
