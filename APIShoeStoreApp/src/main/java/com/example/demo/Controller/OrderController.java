package com.example.demo.Controller;

import com.example.demo.DTO.Request.OrderRequest;
import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.DTO.Response.OrderHistoryResponse;
import com.example.demo.DTO.Response.OrderResponse;
import com.example.demo.Entity.Order;
import com.example.demo.Service.OrderService;
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
@RequestMapping("shoestore/order")
public class OrderController {
    OrderService orderService;

    @PostMapping("/add")
    public APIResponse<OrderResponse> addOrder(@RequestBody OrderRequest orderRequest) {
        APIResponse<OrderResponse> apiResponse = new APIResponse<>();
        apiResponse.setResult(orderService.makeOrder(orderRequest));
        return apiResponse;
    }

    @GetMapping("/history/{user_id}")
    public APIResponse<List<OrderHistoryResponse>> getOrderHistory(@PathVariable long user_id) {
        APIResponse<List<OrderHistoryResponse>> apiResponse = new APIResponse<>();
        apiResponse.setResult(orderService.getOrderHistoryByAccountId(user_id));
        return apiResponse;
    }
}
