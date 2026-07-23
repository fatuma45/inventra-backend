package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findByDeletedFalseOrderByCreatedAtDesc();

    List<Notification> findByDeletedFalseAndIsReadFalseOrderByCreatedAtDesc();

}
