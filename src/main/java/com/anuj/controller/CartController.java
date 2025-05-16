package com.anuj.controller;

//import com.anuj.exception.CartItemException;
//import com.anuj.exception.UserException;
import com.anuj.model.Cart;
import com.anuj.model.CartItem;
import com.anuj.model.Product;
import com.anuj.model.User;
import com.anuj.request.AddItemRequest;
import com.anuj.response.ApiResponse;
import com.anuj.service.CartItemService;
import com.anuj.service.CartService;
import com.anuj.service.ProductService;
import com.anuj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    private final CartItemService cartItemService;



    @GetMapping
    public ResponseEntity<Cart> findUserCartHandler(
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);

        Cart cart=cartService.findUserCart(user);

        System.out.println("cart - "+cart.getUser().getEmail());

        return new ResponseEntity<Cart>(cart,HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);
        Product product=productService.findProductById(req.getProductId());

        CartItem item = cartService.addCartItem(user,
                product,
                req.getSize(),
                req.getQuantity());

        ApiResponse res=new ApiResponse();
        res.setMessage("item add to cart");
        return new ResponseEntity<>(item,HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse>deleteCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization")String jwt)
            throws Exception{

        User user=userService.findUserByJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res=new ApiResponse();
       res.setMessage("Item Remove From Cart");
        return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem>updateCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization")String jwt)
            throws Exception{

        User user=userService.findUserByJwtToken(jwt);

        CartItem updatedCartItem = null;
        if(cartItem.getQuantity()>0){
            updatedCartItem=cartItemService.updateCartItem(user.getId(),
                    cartItemId, cartItem);
        }




        return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
    }
    }