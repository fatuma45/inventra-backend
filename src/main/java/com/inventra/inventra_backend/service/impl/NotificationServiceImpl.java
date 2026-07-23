package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.NotificationRequest;
import com.inventra.inventra_backend.dto.response.NotificationResponse;
import com.inventra.inventra_backend.entity.Notification;
import com.inventra.inventra_backend.entity.User;
import com.inventra.inventra_backend.mapper.NotificationMapper;
import com.inventra.inventra_backend.repository.NotificationRepository;
import com.inventra.inventra_backend.repository.UserRepository;
import com.inventra.inventra_backend.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            UserRepository userRepository) {

        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NotificationResponse create(NotificationRequest request) {

        User user = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        Notification notification = NotificationMapper.toEntity(request);

        notification.setNotificationId(
                String.format("NOT%03d", notificationRepository.count() + 1)
        );

        notification.setCreatedBy(user);

        notificationRepository.save(notification);

        return NotificationMapper.toResponse(notification);
    }

    @Override
    public NotificationResponse getById(String notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Notification not found"));

        return NotificationMapper.toResponse(notification);
    }

    @Override
    public List<NotificationResponse> getAll() {

        return notificationRepository.findByDeletedFalseOrderByCreatedAtDesc()
                .stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponse> getUnread() {

        return notificationRepository.findByDeletedFalseAndIsReadFalseOrderByCreatedAtDesc()
                .stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponse markAsRead(String notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Notification not found"));

        notification.setIsRead(true);

        notificationRepository.save(notification);

        return NotificationMapper.toResponse(notification);
    }

    @Override
    public void delete(String notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Notification not found"));

        notification.setDeleted(true);

        notificationRepository.save(notification);
    }
}
