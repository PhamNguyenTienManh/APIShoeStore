package com.example.demo.Controller;

import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.DTO.Response.ProductDetailResponse;
import com.example.demo.DTO.Response.ProductResponse;
import com.example.demo.Service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@RequestMapping("shoestore/product")
public class ProductController {
    ProductService productService;

    @GetMapping("/all")
    public APIResponse<List<ProductResponse>> getAllProducts ()
    {
        List<ProductResponse> productResponseList = productService.getAllProduct();
        return APIResponse.<List<ProductResponse>>builder()
                .result(productResponseList)
                .build();
    }

    @GetMapping ("/{name}")
    public APIResponse<ProductDetailResponse> getProductByName (@PathVariable String name)
    {
        ProductDetailResponse productDetailResponse = productService.getProductDetail(name);
        return APIResponse.<ProductDetailResponse>builder()
                .result(productDetailResponse)
                .build();
    }

}
