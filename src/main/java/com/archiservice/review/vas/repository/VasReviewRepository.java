package com.archiservice.review.vas.repository;

import com.archiservice.review.vas.domain.VasReview;
import com.archiservice.product.vas.domain.Vas;
import com.archiservice.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VasReviewRepository extends JpaRepository<VasReview, Long> {

    @Query("SELECT vr FROM VasReview vr JOIN FETCH vr.user WHERE vr.vas.vasId = :vasId ORDER BY vr.createdAt DESC")
    Page<VasReview> findByVasIdWithUser(@Param("vasId") Long vasId, Pageable pageable);

    boolean existsByUserAndVas(User user, Vas vas);

}

