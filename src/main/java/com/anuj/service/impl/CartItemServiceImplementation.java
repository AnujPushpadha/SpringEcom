package com.anuj.service.impl;

//import com.anuj.exception.CartItemException;
//import com.anuj.exception.UserException;

import com.anuj.model.CartItem;
import com.anuj.model.User;
import com.anuj.repository.CartItemRepository;
import com.anuj.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImplementation implements CartItemService {

    private CartItemRepository cartItemRepository;


    @Autowired
    public CartItemServiceImplementation(CartItemRepository cartItemRepository) {
        this.cartItemRepository=cartItemRepository;

    }



    @Override
    public CartItem updateCartItem(Long userId,
                                   Long id, CartItem cartItem)
            throws Exception {

        CartItem item=findCartItemById(id);
        User cartItemUser=item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)) {

            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity()*item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellingPrice());

            return cartItemRepository.save(item);


        }
        else {
            throw new Exception("You can't update  another users cart_item");
        }

    }


    @Override
    public void removeCartItem(Long userId,Long cartItemId)
            throws Exception {

        System.out.println("userId- "+userId+" cartItemId "+cartItemId);

        CartItem cartItem=findCartItemById(cartItemId);

        User cartItemUser=cartItem.getCart().getUser();

        if(cartItemUser.getId().equals(userId)) {
            cartItemRepository.deleteById(cartItem.getId());
        }
        else {
            throw new Exception("you can't remove anothor users item");
        }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws Exception {
        Optional<CartItem> opt=cartItemRepository.findById(cartItemId);

        if(opt.isPresent()) {
            return opt.get();
        }
        throw new Exception("cartItem not found with id : "+cartItemId);
    }
}