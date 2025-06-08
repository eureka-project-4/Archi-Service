package com.archiservice.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
* 웹소켓 테스트용으로 user 패키지 만들고 진행,
* 인증인가 구현 완료되면 다 덮어씌워도 됩니다
* */

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;

    private String password;

    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "age_code")
    private String ageCode;

    @Column(name = "tag_code")
    private Long tagCode;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
