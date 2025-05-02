package com.example.demo.Controller;

import com.example.demo.DTO.Request.OrderRequest;
import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.DTO.Response.OrderResponse;
import com.example.demo.Entity.Order;
import com.example.demo.Service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
