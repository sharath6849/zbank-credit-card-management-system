package com.zbank.card.repository;

import com.zbank.card.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {
}