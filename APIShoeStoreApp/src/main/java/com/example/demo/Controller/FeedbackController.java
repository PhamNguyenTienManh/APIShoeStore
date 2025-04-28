package com.example.demo.Controller;


import com.example.demo.DTO.Request.FeedbackRequest;
import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.Entity.Feedback;
import com.example.demo.Service.FeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

