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
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        ProductVariant productVariant = productVariantRepository.findByProductIdAndSize(
                        addToCartRequest.getProductId(), addToCartRequest.getSize())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        Optional<CartItem> optionalItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getVariant().getId().equals(productVariant.getId()))
                .findFirst();

        int existingQuantity = optionalItem.map(CartItem::getQuantity).orElse(0);
        int quantityToAdd = addToCartRequest.getQuantity();
        int totalQuantityAfterAdd = existingQuantity + quantityToAdd;

        if (productVariant.getStock() < totalQuantityAfterAdd) {
            if (existingQuantity == 0) {
                // Lần đầu thêm đã vượt kho
                String message = "Số lượng bạn chọn (" + quantityToAdd + ") vượt quá số lượng còn lại trong kho ("
                        + productVariant.getStock() + ").";
                throw new AppException(ErrorCode.NOT_ENOUGH_STOCK, message);
            } else {
                // Đã có hàng trong giỏ, cộng dồn bị vượt
                String message = "Bạn đã có " + existingQuantity + " sản phẩm trong giỏ hàng. "
                        + "Không thể thêm " + quantityToAdd + " sản phẩm vì sẽ vượt quá số lượng còn lại trong kho ("
                        + productVariant.getStock() + ").";
                throw new AppException(ErrorCode.ADD_CART_OVER_STOCK, message);
            }
        }


        Product product = productVariant.getProduct();

        if (optionalItem.isPresent()) {
            CartItem existingItem = optionalItem.get();
            existingItem.setQuantity(totalQuantityAfterAdd);
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setVariant(productVariant);
            cartItem.setQuantity(addToCartRequest.getQuantity());
            cartItem.setTotal_price(product.getPrice());
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
            cartItemResponse.setImage(cartItem.getVariant().getProduct().getImage());
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
