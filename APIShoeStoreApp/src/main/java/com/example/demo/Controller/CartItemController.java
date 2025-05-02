package com.example.demo.Controller;

import com.example.demo.DTO.Request.CartItemUpdateRequest;
import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.Service.CartItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@RequestMapping("shoestore/cartItem")
public class CartItemController {
    CartItemService cartItemService;
    @PutMapping
    public APIResponse<Boolean> updateCartItems(@RequestBody List<CartItemUpdateRequest> cartItemUpdateRequestList){
        APIResponse<Boolean> apiResponse = new APIResponse<>();
        apiResponse.setResult(cartItemService.updateCartItems(cartItemUpdateRequestList));
        return apiResponse;
    }
}
