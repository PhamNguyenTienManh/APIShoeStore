package com.example.demo.Controller;


import com.example.demo.DTO.Request.FeedbackRequest;
import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.DTO.Response.FeedbackResponse;
import com.example.demo.Entity.Feedback;
import com.example.demo.Service.FeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@RequestMapping("/api/feedback")
public class FeedbackController {
    FeedbackService feedbackService;

    @PostMapping("/create_feedback")
    public APIResponse<Boolean> createFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        APIResponse<Boolean> response = new APIResponse<>();
        response.setResult(feedbackService.saveFeedback(feedbackRequest));
        return response;
    }

    @GetMapping("/{name}")
    public APIResponse<List<FeedbackResponse>> getFeedbacksByProduct(@PathVariable String name) {
        APIResponse<List<FeedbackResponse>> response = new APIResponse<>();
        response.setResult(feedbackService.getFeedbacksByProduct(name));
        return response;
    }
}

