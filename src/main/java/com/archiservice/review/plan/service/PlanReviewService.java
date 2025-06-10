package com.archiservice.review.plan.service;

import com.archiservice.exception.ErrorCode;
import com.archiservice.exception.business.AlreadyReviewedException;
import com.archiservice.exception.business.NotMyReviewException;
import com.archiservice.exception.business.ReviewNotFoundException;
import com.archiservice.exception.business.UserNotFoundException;
import com.archiservice.product.plan.domain.Plan;
import com.archiservice.product.plan.repository.PlanRepository;
import com.archiservice.review.plan.domain.PlanReview;
import com.archiservice.review.plan.dto.request.PlanReviewRequestDto;
import com.archiservice.review.plan.dto.response.PlanReviewResponseDto;
import com.archiservice.review.plan.repository.PlanReviewRepository;
import com.archiservice.user.domain.User;
import com.archiservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.archiservice.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanReviewService {

    private final PlanReviewRepository planReviewRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    @Transactional
    public PlanReviewResponseDto createReview(Long userId, Long planId, PlanReviewRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getMessage()));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND.getMessage()));

        if (planReviewRepository.existsByUserAndPlan(user, plan)) {
            throw new AlreadyReviewedException(ALREADY_REVIEWED.getMessage());
        }

        PlanReview review = PlanReview.builder()
                .user(user)
                .plan(plan)
                .score(requestDto.getScore())
                .content(requestDto.getContent())
                .build();

        PlanReview savedReview = planReviewRepository.save(review);
        return PlanReviewResponseDto.from(savedReview);
    }

    @Transactional
    public PlanReviewResponseDto updateReview(Long userId, Long reviewId, PlanReviewRequestDto requestDto) {
        PlanReview review = planReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND.getMessage()));

        review.validateOwner(userId);

        review.updateReview(requestDto.getScore(), requestDto.getContent());
        return PlanReviewResponseDto.from(review);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        PlanReview review = planReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND.getMessage()));

        review.validateOwner(userId);

        planReviewRepository.delete(review);
    }

    public Page<PlanReviewResponseDto> getReviewsByPlanId(Long planId, Pageable pageable) {
        Page<PlanReview> reviews = planReviewRepository.findByPlanIdWithUser(planId, pageable);
        return reviews.map(PlanReviewResponseDto::from);
    }

    public Page<PlanReviewResponseDto> getReviewsByUserId(Long userId, Pageable pageable) {
        Page<PlanReview> reviews = planReviewRepository.findByUserIdWithPlan(userId, pageable);
        return reviews.map(PlanReviewResponseDto::from);
    }
}
