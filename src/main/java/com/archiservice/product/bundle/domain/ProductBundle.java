package com.archiservice.product.bundle.domain;

import com.archiservice.product.coupon.domain.Coupon;
import com.archiservice.product.plan.domain.Plan;
import com.archiservice.product.vas.domain.Vas;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_bundles")
public class ProductBundle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_bundle_id")
    private Long productBundleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vas_id")
    private Vas vas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private long likeCount;
    private long dislikeCount;
    private long tagCode;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public ProductBundle(Plan plan, Vas vas, Coupon coupon, long tagCode) {
        this.plan = plan;
        this.vas = vas;
        this.coupon = coupon;
        this.likeCount = 0L;
        this.dislikeCount = 0L;
        this.tagCode = tagCode;
    }
}
