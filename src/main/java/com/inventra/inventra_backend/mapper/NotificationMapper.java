package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.request.NotificationRequest;
import com.inventra.inventra_backend.dto.response.NotificationResponse;
import com.inventra.inventra_backend.entity.Notification;

public class NotificationMapper {

    private NotificationMapper() {
    }

    public static Notification toEntity(NotificationRequest request) {

        Notification notification = new Notification();

        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());

        return notification;
    }

    public static NotificationResponse toResponse(Notification notification) {

        NotificationResponse response = new NotificationResponse();

        response.setNotificationId(notification.getNotificationId());
        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());
        response.setType(notification.getType());
        response.setIsRead(notification.getIsRead());

        if (notification.getCreatedBy() != null) {
            response.setCreatedBy(notification.getCreatedBy().getUserId());
        }

        response.setCreatedAt(notification.getCreatedAt());

        return response;
    }
}
