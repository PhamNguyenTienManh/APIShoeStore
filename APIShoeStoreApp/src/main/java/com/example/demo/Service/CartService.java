package com.example.demo.Service;

import com.example.demo.DTO.Request.AddToCartRequest;
import com.example.demo.DTO.Response.CartItemResponse;
import com.example.demo.Entity.Cart;
import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.ProductVariant;
import com.example.demo.Exception.AppException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.Repository.CartItemRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ProductVariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@Service
public class CartService {
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ProductVariantRepository productVariantRepository;


    public boolean addToCart (AddToCartRequest addToCartRequest) {
        Cart cart = cartRepository.findByAccountId(addToCartRequest.getAccountId())
                .orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        // Tìm ProductVariant dựa trên productId và size
        ProductVariant productVariant = productVariantRepository.findByProductIdAndSize(
                addToCartRequest.getProductId(), addToCartRequest.getSize())
                .orElseThrow(()-> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));


        Optional<CartItem> optionalItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getVariant().getId().equals(productVariant.getId()))
                .findFirst();

        // Lấy giá tiền của product variant
        Product product = productVariant.getProduct();

        // Nếu đã có thì tăng số lượng sản phẩm
        if (optionalItem.isPresent()) {
            CartItem existingItem = optionalItem.get();
            int newQuantity = existingItem.getQuantity() + addToCartRequest.getQuantity();
            existingItem.setQuantity(newQuantity);
            // Set lại tổng tiền
            double newTotalPrice = product.getPrice()*newQuantity;
            existingItem.setTotal_price(newTotalPrice);
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setVariant(productVariant);
            cartItem.setQuantity(addToCartRequest.getQuantity());
            double newTotalPrice = product.getPrice()*addToCartRequest.getQuantity();
            cartItem.setTotal_price(newTotalPrice);
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public List<CartItemResponse> getCartItems(Long accountId)
    {
        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        List<CartItem> cartItemList = cartItemRepository.findListCartItemByCartId(cart.getId())
                .orElseThrow(()-> new AppException(ErrorCode.CART_NOT_FOUND));
        List<CartItemResponse> cartItemResponseList = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            CartItemResponse cartItemResponse = new CartItemResponse();
            cartItemResponse.setId(cartItem.getId());
            cartItemResponse.setProductName(cartItem.getVariant().getProduct().getName());
            cartItemResponse.setQuantity(cartItem.getQuantity());
            cartItemResponse.setSize(cartItem.getVariant().getSize());
            cartItemResponse.setTotal_price(cartItem.getTotal_price());
            cartItemResponseList.add(cartItemResponse);
        }
        return cartItemResponseList;
    }

    public boolean deleteCartItemFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.delete(cartItem);
        return true;
    }

}
