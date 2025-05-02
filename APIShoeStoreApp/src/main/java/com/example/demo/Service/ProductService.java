package com.example.demo.Service;

import com.example.demo.DTO.Response.ProductDetailResponse;
import com.example.demo.DTO.Response.ProductResponse;
import com.example.demo.Entity.Feedback;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.ProductVariant;
import com.example.demo.Exception.AppException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.Repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductService {

    ProductRepository productRepository;

    public List<ProductResponse> getAllProduct (){
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            int numstar = 0;
            int i =0;
            ProductResponse productResponse = new ProductResponse();
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            productResponse.setImage(product.getImage());
            List<Feedback> feedbacks = product.getFeedbacks();
            float rateNum = 0;
            if (feedbacks != null && !feedbacks.isEmpty()) {
                for (Feedback feedback : feedbacks) {
                    numstar = numstar + feedback.getRate();
                    i++;
                }
                rateNum = (float) numstar / i;
            }
            productResponse.setFeedbackStar(rateNum);
            productResponses.add(productResponse);
        }
        return productResponses;
    }

    public ProductDetailResponse getProductDetail (String productName) {
        Product product = productRepository.findByName(productName)
                .orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        productDetailResponse.setId(product.getId());
        productDetailResponse.setName(product.getName());
        productDetailResponse.setPrice(product.getPrice());
        productDetailResponse.setImage(product.getImage());
        productDetailResponse.setDescription(product.getDescription());

        // Lấy ra số sao đánh giá của product
        List<Feedback> feedbacks = product.getFeedbacks();
        int numstar = 0;
        int i =0;
        float rateNum = 0;
        if (feedbacks != null && !feedbacks.isEmpty()) {
            for (Feedback feedback : feedbacks) {
                numstar = numstar + feedback.getRate();
                i++;
            }
            rateNum = (float) numstar / i;
        }
        productDetailResponse.setFeedbackStar(rateNum);

        // Lấy ra từng biến thể trong product để lấy kích thước
        List<ProductVariant> variants = product.getVariants();
        Set<Integer> sizes = new HashSet<>();
        for (ProductVariant variant : variants) {
            sizes.add(variant.getSize());
        }
        productDetailResponse.setSize(sizes);

        // Trả về productDetailResponse
        return productDetailResponse;
    }

    public List<ProductResponse> getProductByCategory (String categoryName) {
        List<Product> products = productRepository.findByCategoryName(categoryName);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            int numstar = 0;
            int i =0;
            ProductResponse productResponse = new ProductResponse();
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            productResponse.setImage(product.getImage());
            List<Feedback> feedbacks = product.getFeedbacks();
            float rateNum = 0;
            if (feedbacks != null && !feedbacks.isEmpty()) {
                for (Feedback feedback : feedbacks) {
                    numstar = numstar + feedback.getRate();
                    i++;
                }
                rateNum = (float) numstar / i;
            }
            productResponse.setFeedbackStar(rateNum);
            productResponses.add(productResponse);
        }
        return productResponses;
    }

}
