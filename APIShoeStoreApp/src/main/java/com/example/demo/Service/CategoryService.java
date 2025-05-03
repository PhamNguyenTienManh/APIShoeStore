package com.example.demo.Service;


import com.example.demo.DTO.Response.CategoryResponse;
import com.example.demo.Entity.Category;
import com.example.demo.Repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@Service
public class CategoryService {
    CategoryRepository categoryRepository;
    public List<CategoryResponse> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setName(category.getName());
            categoryResponseList.add(categoryResponse);

        }
        return categoryResponseList;
    }
}
