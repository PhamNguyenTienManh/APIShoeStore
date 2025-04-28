package com.example.demo.Service;


import com.example.demo.DTO.Request.FeedbackRequest;
import com.example.demo.Entity.Account;
import com.example.demo.Entity.Feedback;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.FeedbackRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    public boolean saveFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback();
        Account account = new Account();
        Product product = new Product();
        product.setId(feedbackRequest.getPid());
        account.setId(feedbackRequest.getUser_id());
        feedback.setAccount(account);
        feedback.setComment(feedbackRequest.getComment());
        feedback.setRate(feedbackRequest.getRate());
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setProduct(product);
        feedbackRepository.save(feedback);
        return true;
    }

}
