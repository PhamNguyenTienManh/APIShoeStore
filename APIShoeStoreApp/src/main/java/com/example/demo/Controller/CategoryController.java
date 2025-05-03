package com.example.demo.Controller;


import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.DTO.Response.CategoryResponse;
import com.example.demo.DTO.Response.NotificationResponse;
import com.example.demo.DTO.Response.ProductResponse;
import com.example.demo.Service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@RequestMapping("api/category")
public class CategoryController {
    CategoryService categoryService;

    @GetMapping("all")
    public List<CategoryResponse> getAllCategory()
    {
        return categoryService.getAllCategory();

    }
}
