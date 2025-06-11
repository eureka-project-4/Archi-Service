package com.archiservice.review.coupon.repository;

import com.archiservice.product.coupon.domain.Coupon;
import com.archiservice.review.coupon.domain.CouponReview;
import com.archiservice.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponReviewRepository extends JpaRepository<CouponReview, Long> {
    @Query("SELECT cr FROM CouponReview cr JOIN FETCH cr.user WHERE cr.coupon.couponId = :couponId ORDER BY cr.createdAt DESC")
    Page<CouponReview> findByCouponIdWithUser(@Param("couponId") Long couponId, Pageable pageable);

    boolean existsByUserAndCoupon(User user, Coupon coupon);
}
