package com.archiservice.review.vas.service;

import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.exception.business.AlreadyReviewedException;
import com.archiservice.exception.business.ReviewNotFoundException;
import com.archiservice.exception.business.UserNotFoundException;
import com.archiservice.product.vas.domain.Vas;
import com.archiservice.product.vas.repository.VasRepository;
import com.archiservice.review.vas.domain.VasReview;
import com.archiservice.review.vas.dto.request.VasReviewRequestDto;
import com.archiservice.review.vas.dto.response.VasReviewResponseDto;
import com.archiservice.review.vas.repository.VasReviewRepository;
import com.archiservice.user.domain.User;
import com.archiservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VasReviewServiceImpl implements VasReviewService {

    private final VasReviewRepository vasReviewRepository;
    private final UserRepository userRepository;
    private final VasRepository vasRepository;

    @Transactional
    public VasReviewResponseDto createReview(Long userId, Long serviceId, VasReviewRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        Vas vas = vasRepository.findById(serviceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        // 중복 리뷰 검사
        if (vasReviewRepository.existsByUserAndVas(user, vas)) {
            throw new AlreadyReviewedException(ErrorCode.ALREADY_REVIEWED.getMessage());
        }

        VasReview review = VasReview.builder()
                .user(user)
                .vas(vas)
                .score(requestDto.getScore())
                .content(requestDto.getContent())
                .build();

        VasReview savedReview = vasReviewRepository.save(review);
        return VasReviewResponseDto.from(savedReview);
    }

    @Transactional
    public VasReviewResponseDto updateReview(Long userId, Long reviewId, VasReviewRequestDto requestDto) {
        VasReview review = vasReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND.getMessage()));

        review.validateOwner(userId);
        review.updateReview(requestDto.getScore(), requestDto.getContent());

        return VasReviewResponseDto.from(review);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        VasReview review = vasReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND.getMessage()));

        review.validateOwner(userId);
        vasReviewRepository.delete(review);
    }

    public Page<VasReviewResponseDto> getReviewsByServiceId(Long serviceId, Pageable pageable) {
        Page<VasReview> reviews = vasReviewRepository.findByServiceIdWithUser(serviceId, pageable);
        return reviews.map(VasReviewResponseDto::from);
    }

}
