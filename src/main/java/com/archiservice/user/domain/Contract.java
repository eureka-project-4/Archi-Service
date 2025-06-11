package com.archiservice.user.domain;

import com.archiservice.product.bundle.domain.ProductBundle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_bundle_id")
    private ProductBundle productBundle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String paymentMethod;
    private Long price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public void copyFrom(Contract contract) {
        this.paymentMethod = contract.paymentMethod;
        this.price = contract.price;
        this.productBundle = contract.productBundle;
    }
}
