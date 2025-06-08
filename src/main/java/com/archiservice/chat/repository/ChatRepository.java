package com.archiservice.chat.repository;

import com.archiservice.chat.domain.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatRepository extends JpaRepository<Chat, Long> {

    Page<Chat> findByUser_UserIdAndIsValidTrueOrderByCreatedAtDesc(Long userId, Pageable pageable);

    void deleteByUserId(Long userId);

}
