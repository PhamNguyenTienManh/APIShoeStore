package com.example.demo.Service;

import com.example.demo.DTO.Request.NotificationRequest;
import com.example.demo.DTO.Response.NotificationResponse;
import com.example.demo.Entity.Account;
import com.example.demo.Entity.Notification;
import com.example.demo.Repository.AccountRepository;
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
    private AccountRepository accountRepository;

    public List<NotificationResponse> findById(Long id) {
        Optional<List<Notification>> notifications = Optional.ofNullable(repository.findByAccount_Id(id));

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

    public boolean insertNotification(NotificationRequest notificationRequest) {
        Long userId = notificationRequest.getUser_id();

        if (!accountRepository.existsById(userId)) {
            throw new RuntimeException("Account with ID " + userId + " does not exist.");
        }

        Account account = new Account();
        account.setId(userId);

        Notification notification = new Notification();
        notification.setTitle(notificationRequest.getTitle());
        notification.setMessage(notificationRequest.getContent());
        notification.setCreatedAt(notificationRequest.getTimestamp());
        notification.setAccount(account);

        repository.save(notification);
        return true;
    }




}
