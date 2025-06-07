package com.archiservice.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    private BigInteger userId;
    private String username;
    private String password;
    private String email;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String ageCode;
    private BigInteger tagCode;
}
