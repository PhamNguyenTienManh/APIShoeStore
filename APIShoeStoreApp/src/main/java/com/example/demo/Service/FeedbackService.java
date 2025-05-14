package com.example.demo.Service;


import com.example.demo.DTO.Request.FeedbackRequest;
import com.example.demo.DTO.Response.FeedbackResponse;
import com.example.demo.Entity.Account;
import com.example.demo.Entity.Feedback;
import com.example.demo.Entity.Product;
import com.example.demo.Exception.AppException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.Repository.FeedbackRepository;
import com.example.demo.Repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FeedbackService {
    FeedbackRepository feedbackRepository;
    ProductRepository productRepository;

    public boolean saveFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback();
        Account account = new Account();
        Product product = new Product();
        product = productRepository.findByName(feedbackRequest.getProductName())
                        .orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        account.setId(feedbackRequest.getUser_id());
        feedback.setAccount(account);
        feedback.setComment(feedbackRequest.getComment());
        feedback.setRate(feedbackRequest.getRate());
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setProduct(product);
        feedbackRepository.save(feedback);
        return true;
    }

    public List<FeedbackResponse> getFeedbacksByProduct(String productName) {
        Product product = productRepository.findByName(productName)
                .orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        List<Feedback> feedbackList = feedbackRepository.findByProductId(product.getId());

        List<FeedbackResponse> responseList = new ArrayList<>();
        for (Feedback feedback : feedbackList) {
            FeedbackResponse response = new FeedbackResponse(
                    feedback.getComment(),
                    feedback.getRate(),
                    feedback.getAccount().getFullname()
            );
            responseList.add(response);
        }

        return responseList;
    }



}
