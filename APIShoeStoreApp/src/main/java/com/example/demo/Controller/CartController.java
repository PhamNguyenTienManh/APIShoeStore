package com.example.demo.Controller;

import com.example.demo.DTO.Request.AddToCartRequest;
import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.DTO.Response.CartItemResponse;
import com.example.demo.Entity.Cart;
import com.example.demo.Service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@RequestMapping("shoestore/cart")
public class CartController {
    CartService cartService;

    @PostMapping("/add")
    public APIResponse<Boolean> addToCart (@RequestBody AddToCartRequest request) {
        APIResponse<Boolean> apiResponse = new APIResponse<>();
        apiResponse.setResult(cartService.addToCart(request));
        return apiResponse;
    }

    @GetMapping("/{accountId}")
    public APIResponse<List<CartItemResponse>> getCartItems(@PathVariable Long accountId) {
        APIResponse<List<CartItemResponse>> apiResponse = new APIResponse<>();
        apiResponse.setResult(cartService.getCartItems(accountId));
        return apiResponse;
    }

    @DeleteMapping("delete/{cartItemId}")
    public APIResponse<Boolean> deleteCartItemFromCart(@PathVariable Long cartItemId){
        APIResponse<Boolean> apiResponse = new APIResponse<>();
        apiResponse.setResult( cartService.deleteCartItemFromCart(cartItemId));
        return apiResponse;
    }

}
