package com.example.demo.Service;

import com.example.demo.DTO.Request.CartItemUpdateRequest;
import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.ProductVariant;
import com.example.demo.Exception.AppException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.Repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    @Transactional
    public boolean updateCartItems(List<CartItemUpdateRequest> cartItemUpdateRequestList) {
        for (CartItemUpdateRequest cartItemUpdateRequest : cartItemUpdateRequestList) {
            Optional<CartItem> optionalCartItem =
                    cartItemRepository.findById(cartItemUpdateRequest.getCartItemId());

            ProductVariant productVariant = optionalCartItem.get().getVariant();

            // Kiểm tra số lượng trong kho
            if (productVariant.getStock() < cartItemUpdateRequest.getQuantity()) {
                throw new AppException(ErrorCode.NOT_ENOUGH_STOCK);
            }

            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                int newQuantity = cartItemUpdateRequest.getQuantity();

                // Xóa nếu quantity <= 0
                if (newQuantity <= 0) {
                    cartItemRepository.delete(cartItem);
                } else {
                    // Set lại số lượng
                    cartItem.setQuantity(newQuantity);
                    // Set lại tổng tiền
//                    cartItem.setTotal_price(
//                            cartItem.getVariant().getProduct().getPrice()
//                    );
                    cartItemRepository.save(cartItem);
                }
            }
            else
            {
                throw new AppException(ErrorCode.CART_ITEM_NOT_FOUND);
            }
        }
        return true;
    }

}

