package com.archiservice.chat.domain;

import com.archiservice.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sender sender;

    @Column(name = "is_recommend")
    private Boolean isRecommend;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Sender {
        USER, BOT
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.isValid == null) this.isValid = true;
        if (this.isRecommend == null) this.isRecommend = false;
    }
}