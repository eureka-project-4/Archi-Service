package com.archiservice.review.plan.repository;

import com.archiservice.product.plan.domain.Plan;
import com.archiservice.review.plan.domain.PlanReview;
import com.archiservice.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanReviewRepository extends JpaRepository<PlanReview, Long> {
    // 특정 요금제의 리뷰 조회 (User 정보 포함)
    @Query("SELECT pr FROM PlanReview pr JOIN FETCH pr.user WHERE pr.plan.planId = :planId ORDER BY pr.createdAt DESC")
    Page<PlanReview> findByPlanIdWithUser(@Param("planId") Long planId, Pageable pageable);

    // 특정 사용자의 리뷰 조회 (Plan 정보 포함)
    @Query("SELECT pr FROM PlanReview pr JOIN FETCH pr.plan WHERE pr.user.userId = :userId ORDER BY pr.createdAt DESC")
    Page<PlanReview> findByUserIdWithPlan(@Param("userId") Long userId, Pageable pageable);

    // 사용자와 요금제로 리뷰 존재 여부 확인
    boolean existsByUserAndPlan(User user, Plan plan);

    // 사용자와 요금제로 리뷰 조회
    Optional<PlanReview> findByUserAndPlan(User user, Plan plan);

    // 특정 요금제의 평균 평점
    @Query("SELECT AVG(pr.score) FROM PlanReview pr WHERE pr.plan.planId = :planId")
    Double getAverageScoreByPlanId(@Param("planId") Long planId);

    // 특정 요금제의 리뷰 개수
    Long countByPlan(Plan plan);
}
