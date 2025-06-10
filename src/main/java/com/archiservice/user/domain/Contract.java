package com.archiservice.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "contracts")
public class Contract {
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
