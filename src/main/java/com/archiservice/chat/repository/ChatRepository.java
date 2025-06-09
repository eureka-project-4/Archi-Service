package com.archiservice.chat.repository;

import com.archiservice.chat.domain.Chat;
import com.archiservice.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, Long> {

    Page<Chat> findByUserAndIsValidTrueOrderByCreatedAtDesc(User user, Pageable pageable);
    void deleteByUser_UserId(Long userId);

}
