package com.archiservice.user.repository;

import com.archiservice.user.domain.Contracts;
import com.archiservice.user.domain.User;
import com.archiservice.user.dto.response.ContractOnlyResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractsRepository extends JpaRepository<Contracts, Long> {

    @Query(value =
            "SELECT pb.plan_id " +
            "FROM contracts c " +
            "JOIN product_bundles pb ON c.product_bundle_id = pb.product_bundle_id " +
            "WHERE c.user_id = :userId " +
            "ORDER BY c.contract_id DESC " +
            "LIMIT 1 OFFSET 1",
            nativeQuery = true)
    Optional<Long> findCurrentPlanId(@Param("userId") Long userId);

    @Query(value =
            "SELECT pb.service_id " +
            "FROM contracts c " +
            "JOIN product_bundles pb ON c.product_bundle_id = pb.product_bundle_id " +
            "WHERE c.user_id = :userId " +
            "ORDER BY c.contract_id DESC " +
            "LIMIT 1 OFFSET 1",
            nativeQuery = true)
    Optional<Long> findCurrentVasId(@Param("userId") Long userId);

    @Query(value =
            "SELECT pb.coupon_id " +
            "FROM contracts c " +
            "JOIN product_bundles pb ON c.product_bundle_id = pb.product_bundle_id " +
            "WHERE c.user_id = :userId " +
            "ORDER BY c.contract_id DESC " +
            "LIMIT 1 OFFSET 1",
            nativeQuery = true)
    Optional<Long> findCurrentCouponId(@Param("userId") Long userId);

    @Query("SELECT new com.archiservice.user.dto.response.ContractOnlyResponseDto(" +
            "c.paymentMethod, c.price, c.startDate, c.endDate) " +
            "FROM Contracts c " +
            "WHERE c.user = :user " +
            "ORDER BY c.id DESC " +
            "LIMIT 1 OFFSET 1")
    Optional<ContractOnlyResponseDto> findCurrentContract(@Param("user") User user);

    @Query(value =
            "SELECT pb.plan_id " +
            "FROM contracts c " +
            "JOIN product_bundles pb ON c.product_bundle_id = pb.product_bundle_id " +
            "WHERE c.user_id = :userId " +
            "ORDER BY c.contract_id DESC " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Long> findNextPlanId(@Param("userId") Long userId);

    @Query(value =
            "SELECT pb.service_id " +
            "FROM contracts c " +
            "JOIN product_bundles pb ON c.product_bundle_id = pb.product_bundle_id " +
            "WHERE c.user_id = :userId " +
            "ORDER BY c.contract_id DESC " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Long> findNextVasId(@Param("userId") Long userId);

    @Query(value =
            "SELECT pb.coupon_id " +
            "FROM contracts c " +
            "JOIN product_bundles pb ON c.product_bundle_id = pb.product_bundle_id " +
            "WHERE c.user_id = :userId " +
            "ORDER BY c.contract_id DESC " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Long> findNextCouponId(@Param("userId") Long userId);

    @Query("SELECT new com.archiservice.user.dto.response.ContractOnlyResponseDto(" +
            "c.paymentMethod, c.price, c.startDate, c.endDate) " +
            "FROM Contracts c " +
            "WHERE c.user = :user " +
            "ORDER BY c.id DESC " +
            "LIMIT 1")
    Optional<ContractOnlyResponseDto> findNextContract(@Param("user") User user);

    default Optional<Long> findPlanIdByPeriod(Long userId, boolean isCurrent) {
        return isCurrent ? findCurrentPlanId(userId) : findNextPlanId(userId);
    }

    default Optional<Long> findVasIdByPeriod(Long userId, boolean isCurrent) {
        return isCurrent ? findCurrentVasId(userId) : findNextVasId(userId);
    }

    default Optional<Long> findCouponIdByPeriod(Long userId, boolean isCurrent) {
        return isCurrent ? findCurrentCouponId(userId) : findNextCouponId(userId);
    }

    default Optional<ContractOnlyResponseDto> findContractByPeriod(User user, boolean isCurrent) {
        return isCurrent ? findCurrentContract(user) : findNextContract(user);
    }
}
