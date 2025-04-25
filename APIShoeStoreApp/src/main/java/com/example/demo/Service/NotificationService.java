package com.example.demo.Service;

import com.example.demo.DTO.Response.NotificationResponse;
import com.example.demo.Entity.Notification;
import com.example.demo.Repository.NotificationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationService {

    private NotificationRepository repository;

    public List<NotificationResponse> findById(Long id) {
        Optional<List<Notification>> notifications = Optional.ofNullable(repository.findAllById(Collections.singleton(id)));

        // Extract the list from the Optional
        if (notifications.isPresent()) {
            List<NotificationResponse> responses = new ArrayList<>();
            for (Notification notification : notifications.get()) {
                NotificationResponse response = new NotificationResponse();
                response.setMessage(notification.getMessage());
                response.setTitle(notification.getTitle());
                response.setTimestamp(notification.getCreatedAt());
                responses.add(response);
            }
            return responses;
        } else {
            return new ArrayList<>();
        }
    }



}
