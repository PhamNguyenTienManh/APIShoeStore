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
        StringBuilder errorMessageBuilder = new StringBuilder("Không đủ số lượng trong kho cho các sản phẩm sau:\n");

        boolean hasError = false;

        for (CartItemUpdateRequest cartItemUpdateRequest : cartItemUpdateRequestList) {
            Optional<CartItem> optionalCartItem =
                    cartItemRepository.findById(cartItemUpdateRequest.getCartItemId());

            if (optionalCartItem.isEmpty()) {
                throw new AppException(ErrorCode.CART_ITEM_NOT_FOUND);
            }

            CartItem cartItem = optionalCartItem.get();
            ProductVariant productVariant = cartItem.getVariant();

            int requestedQuantity = cartItemUpdateRequest.getQuantity();
            int availableStock = productVariant.getStock();

            if (availableStock < requestedQuantity) {
                hasError = true;
                errorMessageBuilder.append(String.format(
                        "Sản phẩm: %s (Size: %s) chỉ còn %d trong kho. Bạn không thể thêm %d sản phẩm.\n",
                        productVariant.getProduct().getName(),
                        productVariant.getSize(),
                        availableStock,
                        requestedQuantity
                ));
            }
        }

        if (hasError) {
            throw new AppException(ErrorCode.NOT_ENOUGH_STOCK, errorMessageBuilder.toString());
        }

        // Nếu không có lỗi, tiến hành cập nhật
        for (CartItemUpdateRequest cartItemUpdateRequest : cartItemUpdateRequestList) {
            CartItem cartItem = cartItemRepository.findById(cartItemUpdateRequest.getCartItemId()).get();
            int newQuantity = cartItemUpdateRequest.getQuantity();

            if (newQuantity <= 0) {
                cartItemRepository.delete(cartItem);
            } else {
                cartItem.setQuantity(newQuantity);
                cartItemRepository.save(cartItem);
            }
        }

        return true;
    }


}

