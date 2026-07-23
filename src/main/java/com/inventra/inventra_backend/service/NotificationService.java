package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.NotificationRequest;
import com.inventra.inventra_backend.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {

    NotificationResponse create(NotificationRequest request);

    NotificationResponse getById(String notificationId);

    List<NotificationResponse> getAll();

    List<NotificationResponse> getUnread();

    NotificationResponse markAsRead(String notificationId);

    void delete(String notificationId);
}
