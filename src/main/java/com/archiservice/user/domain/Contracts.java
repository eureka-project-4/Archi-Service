package com.archiservice.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Contracts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    // 아직 없음
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_bundle_id")
//    private ProductBundle bundleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String paymentMethod;
    private Long price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
