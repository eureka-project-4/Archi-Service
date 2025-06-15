package com.archiservice.chatbot.repository;

import com.archiservice.chatbot.domain.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, Long> {
    void deleteByUser_UserId(Long userId);

    Page<Chat> findByUser_UserId(Long userId, Pageable pageable);
}
