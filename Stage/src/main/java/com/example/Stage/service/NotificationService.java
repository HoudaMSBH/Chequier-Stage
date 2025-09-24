package com.example.Stage.service;

import com.example.Stage.entity.Notification;
import com.example.Stage.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepo;

    public NotificationService(NotificationRepository notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepo.findAll();
    }

    public Notification saveNotification(Notification notification) {
        return notificationRepo.save(notification);
    }
}
