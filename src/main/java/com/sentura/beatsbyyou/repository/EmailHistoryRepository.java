package com.sentura.beatsbyyou.repository;

import com.sentura.beatsbyyou.entity.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailHistoryRepository extends JpaRepository<EmailHistory,Long> {
}
