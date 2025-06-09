package com.archiservice.product.coupon.repository;

import com.archiservice.product.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}

