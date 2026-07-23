package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.NotificationRequest;
import com.inventra.inventra_backend.dto.response.NotificationResponse;
import com.inventra.inventra_backend.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse create(
            @RequestBody NotificationRequest request) {

        return notificationService.create(request);
    }

    @GetMapping
    public List<NotificationResponse> getAll() {

        return notificationService.getAll();
    }

    @GetMapping("/unread")
    public List<NotificationResponse> getUnread() {

        return notificationService.getUnread();
    }

    @GetMapping("/{notificationId}")
    public NotificationResponse getById(
            @PathVariable String notificationId) {

        return notificationService.getById(notificationId);
    }

    @PutMapping("/{notificationId}/read")
    public NotificationResponse markAsRead(
            @PathVariable String notificationId) {

        return notificationService.markAsRead(notificationId);
    }

    @DeleteMapping("/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String notificationId) {

        notificationService.delete(notificationId);
    }
}
