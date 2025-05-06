package com.example.demo.Service;


import com.example.demo.DTO.Request.OrderRequest;
import com.example.demo.DTO.Response.OrderDetailResponse;
import com.example.demo.DTO.Response.OrderHistoryResponse;
import com.example.demo.DTO.Response.OrderResponse;
import com.example.demo.Entity.*;
import com.example.demo.Enum.OrderStatus;
import com.example.demo.Enum.PaymentType;
import com.example.demo.Exception.AppException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.Repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderService {
    OrderRepository orderRepository;

    AccountRepository accountRepository;
    CartRepository cartRepository;
    PaymentRepository paymentRepository;
    ProductVariantRepository productVariantRepository;

    @Transactional
    public OrderResponse makeOrder(OrderRequest requestInfo) {
        Account account = accountRepository.findById(requestInfo.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Cart cart = cartRepository.findByAccountId(requestInfo.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        if (cart.getCartItems().isEmpty()) {
            throw new AppException(ErrorCode.CART_IS_EMPTY);
        }

        // Check số lượng tồn kho hợp lệ
        for (CartItem cartItem : cart.getCartItems()) {
            ProductVariant variant = cartItem.getVariant();
            int quantity = cartItem.getQuantity();

            // Kiểm tra số lượng trong kho
            if (variant.getStock() < quantity) {
                throw new AppException(ErrorCode.NOT_ENOUGH_STOCK);
            }
        }

        Order order = Order.builder()
            .account(account)
            .name(requestInfo.getName())
            .phone(requestInfo.getPhone())
            .province(requestInfo.getProvince())
            .district(requestInfo.getDistrict())
            .commune(requestInfo.getCommune())
            .detailedAddress(requestInfo.getDetailedAddress())
            .orderDate(LocalDateTime.now())
            .status(OrderStatus.PENDING)
            .build();

        Double total = 0.0;
        List<OrderDetail> orderDetailList = new ArrayList<>();
        List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();

        // Lặp qua từng cartItem trong Cart để lưu vào orderDetail
        for (CartItem cartItem : cart.getCartItems()) {
            ProductVariant variant = cartItem.getVariant();
            Product product = variant.getProduct();
            Double price = product.getPrice();
            int quantity = cartItem.getQuantity();
            Double itemTotal = price * quantity;

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setVariant(variant);
            detail.setPrice(price);
            detail.setQuantity(quantity);
            detail.setTotal(itemTotal);

            orderDetailList.add(detail);
            total += itemTotal;

            // Cập nhật lại số lượng product variant
            variant.setStock(variant.getStock() - quantity);
            productVariantRepository.save(variant);

            // Mapping orderDetail vào orderDetailResponse
            OrderDetailResponse detailResponse = new OrderDetailResponse();
            detailResponse.setVariantId(variant.getId());
            detailResponse.setProductName(product.getName());
            detailResponse.setPrice(price);
            detailResponse.setQuantity(quantity);
            detailResponse.setTotal(itemTotal);
            orderDetailResponseList.add(detailResponse);
        }

        order.setOrderDetails(orderDetailList);
        order.setTotal(total);

        Order savedOrder = orderRepository.save(order);


        // Xóa cartItem trong cart
        cart.getCartItems().clear();
        // Lưu lại cart để đồng bộ hóa thay đổi vào cơ sở dữ liệu
        cartRepository.save(cart);


        // Lưu thông tin thanh toán xuống csdl
        Payment payment = Payment.builder()
                .order(savedOrder)
                .type(PaymentType.valueOf(requestInfo.getPaymentMethod()))
                .date(LocalDate.now())
                .totalPrice(total)
                .build();
        paymentRepository.save(payment);


        // Trả về orderResponse
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getName(),
                savedOrder.getPhone(),
                savedOrder.getProvince(),
                savedOrder.getDistrict(),
                savedOrder.getCommune(),
                savedOrder.getDetailedAddress(),
                savedOrder.getOrderDate(),
                savedOrder.getTotal(),
                savedOrder.getStatus(),
                orderDetailResponseList
        );
    }

    public List<OrderHistoryResponse> getOrderHistoryByAccountId(Long userId) {
        try {
            return orderRepository.getOrderSummariesByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
