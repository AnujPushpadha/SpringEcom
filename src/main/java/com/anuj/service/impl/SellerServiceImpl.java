package com.anuj.service.impl;

import com.anuj.config.JwtProvider;
import com.anuj.domain.AccountStatus;
import com.anuj.domain.USER_ROLE;
//import com.anuj.exception.SellerException;
import com.anuj.exception.SellerException;
import com.anuj.model.Address;
import com.anuj.model.Seller;
import com.anuj.repository.AddressRepository;
import com.anuj.repository.SellerRepository;
import com.anuj.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
        if (sellerExist != null) {
            throw new Exception("Seller already exists used different email");
        }

        Address savedAddress = addressRepository.save(seller.getPickupAddress());


        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPickupAddress(savedAddress);
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());

        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        System.out.println(newSeller);
        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        if (optionalSeller.isPresent()) {
            return optionalSeller.get();
        }
        throw new SellerException("Seller not found");
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller != null) {
            return seller;
        }
        throw new Exception("Seller not found");
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
//        Seller existingSeller = sellerRepository.findById(id)
//          .orElseThrow(() ->
//          new Exception("Seller not found with id " + id));

           Seller existingSeller=this.getSellerById(id);
        if (seller.getSellerName() != null) {
            existingSeller.setSellerName(seller.getSellerName());
        }
        if (seller.getMobile() != null) {
            existingSeller.setMobile(seller.getMobile());
        }
        if (seller.getEmail() != null) {
            existingSeller.setEmail(seller.getEmail());
        }

        if (seller.getBusinessDetails() != null
                && seller.getBusinessDetails().getBusinessName() != null
        ) {

            existingSeller.getBusinessDetails().setBusinessName(
                    seller.getBusinessDetails().getBusinessName()
            );
        }

        if (seller.getBankDetails() != null
                && seller.getBankDetails().getAccountHolderName() != null
                && seller.getBankDetails().getIfscCode() != null
                && seller.getBankDetails().getAccountNumber() != null
        ) {

            existingSeller.getBankDetails().setAccountHolderName(
                    seller.getBankDetails().getAccountHolderName()
            );
            existingSeller.getBankDetails().setAccountNumber(
                    seller.getBankDetails().getAccountNumber()
            );
            existingSeller.getBankDetails().setIfscCode(
                    seller.getBankDetails().getIfscCode()
            );
        }
        if (seller.getPickupAddress() != null
                && seller.getPickupAddress().getAddress() != null
                && seller.getPickupAddress().getMobile() != null
                && seller.getPickupAddress().getCity() != null
                && seller.getPickupAddress().getState() != null
        ) {
            existingSeller.getPickupAddress()
                    .setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
            existingSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());
        }
        if (seller.getGSTIN() != null) {
            existingSeller.setGSTIN(seller.getGSTIN());
        }


        return sellerRepository.save(existingSeller);

    }

    @Override
    public void deleteSeller(Long id) throws Exception {
//        if (sellerRepository.existsById(id)) {
//            sellerRepository.deleteById(id);
//        } else {
//            throw new Exception("Seller not found with id " + id);
//        }
        Seller seller=getSellerById(id);
        sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
//        Seller seller = this.getSellerByEmail(email);
//        seller.setEmailVerified(true);
//        return sellerRepository.save(seller);

        Seller seller =getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
//        Seller seller = this.getSellerById(sellerId);
//        seller.setAccountStatus(status);
//        return sellerRepository.save(seller);

        Seller seller =getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}