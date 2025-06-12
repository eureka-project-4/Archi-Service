package com.archiservice.product.bundle.repository;

import com.archiservice.product.bundle.domain.ProductBundle;
import com.archiservice.product.coupon.domain.Coupon;
import com.archiservice.product.plan.domain.Plan;
import com.archiservice.product.vas.domain.Vas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductBundleRepository extends JpaRepository<ProductBundle, Long>{

    Optional<ProductBundle> findProductBundleByPlan_PlanIdAndVas_VasIdAndCoupon_CouponId(Long planId, Long vasId, Long couponId);
    long findProductBundle_ProductBundleIdByPlanAndVasAndCoupon(Plan plan, Vas vas, Coupon coupon);

    @Modifying
    @Query("UPDATE ProductBundle pb SET pb.likeCount = pb.likeCount + 1 WHERE pb.productBundleId = :productBundleId")
    int incrementLikeCount(@Param("productBundleId") Long productBundleId);

    @Modifying
    @Query("UPDATE ProductBundle pb SET pb.dislikeCount = pb.dislikeCount + 1 WHERE pb.productBundleId = :productBundleId")
    int incrementDislikeCount(@Param("productBundleId") Long productBundleId);

    boolean existsByPlanAndVasAndCoupon(Plan plan, Vas vas, Coupon coupon);
}
