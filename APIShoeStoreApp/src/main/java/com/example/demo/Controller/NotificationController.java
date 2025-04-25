package com.example.demo.Controller;


import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.DTO.Response.NotificationResponse;
import com.example.demo.Service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationController {
    NotificationService notificationService;
    @GetMapping("/{id}")
    @ResponseBody
    public List<NotificationResponse> getAllNotificationById (@PathVariable Long id){
        return notificationService.findById(id);
    }

}
