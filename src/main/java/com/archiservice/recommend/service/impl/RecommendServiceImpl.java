package com.archiservice.recommend.service.impl;


import com.archiservice.code.commoncode.service.CommonCodeService;
import com.archiservice.code.tagmeta.service.TagMetaService;
import com.archiservice.common.security.CustomUser;
import com.archiservice.product.coupon.domain.Coupon;
import com.archiservice.product.coupon.dto.response.CouponDetailResponseDto;
import com.archiservice.product.coupon.repository.CouponRepository;
import com.archiservice.product.plan.domain.Plan;
import com.archiservice.product.plan.dto.response.PlanDetailResponseDto;
import com.archiservice.product.plan.repository.PlanRepository;
import com.archiservice.product.vas.domain.Vas;
import com.archiservice.product.vas.dto.response.VasDetailResponseDto;
import com.archiservice.product.vas.repository.VasRepository;
import com.archiservice.recommend.dto.response.RecommendCouponResponseDto;
import com.archiservice.recommend.dto.response.RecommendPlanResponseDto;
import com.archiservice.recommend.dto.response.RecommendResponseDto;
import com.archiservice.recommend.dto.response.RecommendVasResponseDto;
import com.archiservice.recommend.service.RecommendService;
import com.archiservice.review.coupon.repository.CouponReviewRepository;
import com.archiservice.review.plan.repository.PlanReviewRepository;
import com.archiservice.review.vas.repository.VasReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendServiceImpl implements RecommendService {

    // TODO : 각 상품의 전체 평균 리뷰 수 구하고, 해당값 베이지안 계산시 사용
    final int MIN_REVIEWS = 10;

    private final TagMetaService tagMetaService;
    private final PlanRepository planRepository;
    private final VasRepository vasRepository;

    private final PlanReviewRepository planReviewRepository;
    private final VasReviewRepository vasReviewRepository;
    private final CouponReviewRepository couponReviewRepository;

    private final CouponRepository couponRepository;
    private final CommonCodeService commonCodeService;

    @Override
    public RecommendResponseDto recommend(CustomUser user) {

        List<String> userTagcodes = tagMetaService.extractTagsFromCode(user.getUser().getTagCode());
        System.out.print("현재 사용자의 태그 코드 : {");
        for(String tagCode : userTagcodes) {
            System.out.print(tagCode + " ");
        }
        System.out.println("}");

        // 1. 요금제 추천
        RecommendPlanResponseDto recommendedPlans = recommendPlan(user);
        // 2. 부가서비스 추천 정렬
        RecommendVasResponseDto recommendedVass = recommendVas(user);
        // 3. 쿠폰 추천 정렬
        RecommendCouponResponseDto recommendedCoupons = recommendCoupon(user);

        // TODO : 성향태그가 아무것도 겹치지않는 경우 예외 고려
        return RecommendResponseDto.from( recommendedPlans, recommendedVass, recommendedCoupons );

    }

    // TODO : 쿼리가 너무 많이 실행되서 redis 써야할듯

//    @Override
//    public RecommendPlanResponseDto recommendPlan(CustomUser user) {
//        long userTagCode = user.getUser().getTagCode();  // 사용자 태그코드
//        double globalPlanAvg = getGlobalAvg("plan");  // 전체 요금제의 평균 리뷰 점수
//
//        // 모든 요금제 조회 및 비교 시작
//        List<PlanDetailResponseDto> recommendedPlans = planRepository.findAll().stream()
//                .map(plan -> {
//                    int bitCount = Long.bitCount(userTagCode & plan.getTagCode());
//                    return Map.entry(plan, bitCount);  // 각 요금제(plan)와 사용자 tagCode를 AND 비트 연산한 후 1의 개수 세기 -> 유사도 평가
//                })
//                //TODO : 성향태그가 아무것도 겹치지않는 경우 예외 고려하기
//                .filter(entry -> entry.getValue() > 0) // 겹치는 태그가 하나도 없는 경우 제외
//                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // 비트카운트(유사도)를 기준으로 내림차순 정렬
//                .limit(10) // 상위 10개만 추출 -> 평점 기반 정렬을 위한 후보
//                .map(entry -> {
//                    Plan plan = entry.getKey();
//                    int reviewCount = planReviewRepository.countPlanReviewByPlan(plan); // 요금제의 리뷰 수 조회
//                    double avgRating = Optional.ofNullable(
//                            planReviewRepository.getAverageRatingByPlan(plan) // 요금제의 평균 평점 조회
//                    ).orElse(0.0);
//                    double bayesScore = computeBayesianAverage(avgRating, reviewCount, globalPlanAvg, MIN_REVIEWS); // Bayesian 평균 점수 계산
//
//                    return new AbstractMap.SimpleEntry<>(plan, bayesScore);
//                })
//                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())) // Bayesian 점수 기준으로 내림차순 정렬
//                .limit(5)
//                .map(Map.Entry::getKey)
//                .map(plan -> {
//                    List<String> tags = tagMetaService.extractTagsFromCode(plan.getTagCode());
//                    String category = commonCodeService.getCodeName("G02", plan.getCategoryCode());
//                    String targetAge = commonCodeService.getCodeName("G01", plan.getAgeCode());
//                    return PlanDetailResponseDto.from(plan, tags, category, targetAge);
//                })
//                .toList();
//
//        return new RecommendPlanResponseDto(recommendedPlans);
//    }
//
//
//    @Override
//    public RecommendVasResponseDto recommendVas(CustomUser user) {
//        long userTagCode = user.getUser().getTagCode();
//        double globalVasAvg = getGlobalAvg("vas");
//
//        List<VasDetailResponseDto> recommendedVass = vasRepository.findAll().stream()
//                .map(vas -> {
//                    int bitCount = Long.bitCount(userTagCode & vas.getTagCode());
//                    return Map.entry(vas, bitCount);
//                })
//                .filter(entry -> entry.getValue() > 0)
//                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
//                .limit(10)
//                .map(entry -> {
//                    Vas vas = entry.getKey();
//                    int reviewCount = vasReviewRepository.countVasReviewByVas(vas);
//                    double avgRating = Optional.ofNullable(
//                            vasReviewRepository.getAverageRatingByVas(vas)).orElse(0.0);
//                    double bayesScore = computeBayesianAverage(avgRating, reviewCount, globalVasAvg, MIN_REVIEWS);
//                    return new AbstractMap.SimpleEntry<>(vas, bayesScore);
//                })
//                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
//                .limit(5)
//                .map(Map.Entry::getKey)
//                .map(vas -> {
//                    List<String> tags = tagMetaService.extractTagsFromCode(vas.getTagCode());
//                    String category = commonCodeService.getCodeName("G03", vas.getCategoryCode());
//                    return VasDetailResponseDto.from(vas, tags, category);
//                })
//                .toList();
//
//        return new RecommendVasResponseDto(recommendedVass);
//    }
//
//
//    @Override
//    public RecommendCouponResponseDto recommendCoupon(CustomUser user) {
//        long userTagCode = user.getUser().getTagCode();
//        double globalCouponAvg = getGlobalAvg("coupon");
//
//        List<CouponDetailResponseDto> recommendedCoupons = couponRepository.findAll().stream()
//                .map(coupon -> Map.entry(coupon, Long.bitCount(userTagCode & coupon.getTagCode())))
//                .filter(entry -> entry.getValue() > 0)
//                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
//                .limit(10)
//                .map(entry -> {
//                    Coupon coupon = entry.getKey();
//
//                    int reviewCount = couponReviewRepository.countCouponReviewByCoupon(coupon);
//                    double avgRating = Optional.ofNullable(
//                            couponReviewRepository.getAverageRatingByCoupon(coupon)).orElse(0.0);
//
//                    double bayesScore = computeBayesianAverage(avgRating, reviewCount, globalCouponAvg, MIN_REVIEWS);
//                    return new AbstractMap.SimpleEntry<>(coupon, bayesScore);
//                })
//                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
//                .limit(5)
//                .map(Map.Entry::getKey)
//                .map(coupon -> {
//                    List<String> tags = tagMetaService.extractTagsFromCode(coupon.getTagCode());
//                    String category = commonCodeService.getCodeName("G04", coupon.getCategoryCode());
//                    return CouponDetailResponseDto.from(coupon, tags, category);
//                })
//                .toList();
//
//        return new RecommendCouponResponseDto(recommendedCoupons);
//    }


    public double getGlobalAvg(String type) {
        return switch (type.toLowerCase()) {
            case "plan" -> Optional.ofNullable(planReviewRepository.findAverageRatingByPlanIsNotNull()).orElse(0.0);
            case "vas" -> Optional.ofNullable(vasReviewRepository.findAverageRatingByVasIsNotNull()).orElse(0.0);
            case "coupon" -> Optional.ofNullable(couponReviewRepository.findAverageRatingByCouponIsNotNull()).orElse(0.0);
            default -> 0.0;
        };
    }

    // 베이지안 평균 계산
    //                            해당 상품의 평균 평점 , 해당 상품에 달린 총 리뷰 수 , 전체 상품의 전역 평균 평점 , 신뢰도 기준으로 삼을 최소 리뷰 수
    private double computeBayesianAverage(double avgRating, int reviewCount, double globalAvg, int minReviews) {
        return (reviewCount / (double)(reviewCount + minReviews)) * avgRating +
                (minReviews / (double)(reviewCount + minReviews)) * globalAvg;
    }

    /**********출력용**********/

    @Override
    public RecommendPlanResponseDto recommendPlan(CustomUser user) {
        long userTagCode = user.getUser().getTagCode();
        double globalPlanAvg = getGlobalAvg("plan");

        List<Map.Entry<Plan, Integer>> bitCountSorted = planRepository.findAll().stream()
                .map(plan -> Map.entry(plan, Long.bitCount(userTagCode & plan.getTagCode())))
                .filter(entry -> entry.getValue() > 0)
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // 비트카운트 기준 정렬
                .limit(10)
                .toList();

        List<ProductWithScore<Plan>> topPlans = bitCountSorted.stream()
                .map(entry -> {
                    Plan plan = entry.getKey();
                    int bitCount = entry.getValue();
                    int reviewCount = planReviewRepository.countPlanReviewByPlan(plan);
                    double avgRating = Optional.ofNullable(
                            planReviewRepository.getAverageRatingByPlan(plan)
                    ).orElse(0.0);
                    double bayesScore = computeBayesianAverage(avgRating, reviewCount, globalPlanAvg, MIN_REVIEWS);
                    return new ProductWithScore<>(plan, bitCount, bayesScore);
                })
                .sorted((p1, p2) -> Double.compare(p2.bayesScore, p1.bayesScore))
                .limit(5)
                .toList();

        List<PlanDetailResponseDto> recommendedPlans = topPlans.stream()
                .map(p -> {
                    List<String> tags = tagMetaService.extractTagsFromCode(p.product.getTagCode());
                    String category = commonCodeService.getCodeName("G02", p.product.getCategoryCode());
                    String targetAge = commonCodeService.getCodeName("G01", p.product.getAgeCode());
                    return PlanDetailResponseDto.from(p.product, tags, category, targetAge);
                })
                .toList();

        System.out.println("🔽 최종 추천된 요금제 5개:");
        for (ProductWithScore<Plan> p : topPlans) {
            System.out.printf("- planId: %d, name: %s, bitCount: %d, bayesScore: %.2f%n",
                    p.product.getPlanId(), p.product.getPlanName(), p.bitCount, p.bayesScore);
        }

        return new RecommendPlanResponseDto(recommendedPlans);
    }


    @Override
    public RecommendVasResponseDto recommendVas(CustomUser user) {
        long userTagCode = user.getUser().getTagCode();
        double globalVasAvg = getGlobalAvg("vas");

        List<ProductWithScore<Vas>> topVass = vasRepository.findAll().stream()
                .map(vas -> Map.entry(vas, Long.bitCount(userTagCode & vas.getTagCode())))
                .filter(entry -> entry.getValue() > 0)
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .map(entry -> {
                    Vas vas = entry.getKey();
                    int bitCount = entry.getValue();
                    int reviewCount = vasReviewRepository.countVasReviewByVas(vas);
                    double avgRating = Optional.ofNullable(
                            vasReviewRepository.getAverageRatingByVas(vas)).orElse(0.0);
                    double bayesScore = computeBayesianAverage(avgRating, reviewCount, globalVasAvg, MIN_REVIEWS);
                    return new ProductWithScore<>(vas, bitCount, bayesScore);
                })
                .sorted((v1, v2) -> Double.compare(v2.bayesScore, v1.bayesScore))
                .limit(5)
                .toList();

        List<VasDetailResponseDto> recommendedVass = topVass.stream()
                .map(p -> {
                    List<String> tags = tagMetaService.extractTagsFromCode(p.product.getTagCode());
                    String category = commonCodeService.getCodeName("G03", p.product.getCategoryCode());
                    return VasDetailResponseDto.from(p.product, tags, category);
                })
                .toList();

        System.out.println("🔽 최종 추천된 VAS 5개:");
        for (ProductWithScore<Vas> p : topVass) {
            System.out.printf("- vasId: %d, name: %s, bitCount: %d, bayesScore: %.2f%n",
                    p.product.getVasId(), p.product.getVasName(), p.bitCount, p.bayesScore);
        }

        return new RecommendVasResponseDto(recommendedVass);
    }


    @Override
    public RecommendCouponResponseDto recommendCoupon(CustomUser user) {
        long userTagCode = user.getUser().getTagCode();
        double globalCouponAvg = getGlobalAvg("coupon");

        List<ProductWithScore<Coupon>> topCoupons = couponRepository.findAll().stream()
                .map(coupon -> Map.entry(coupon, Long.bitCount(userTagCode & coupon.getTagCode())))
                .filter(entry -> entry.getValue() > 0)
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .map(entry -> {
                    Coupon coupon = entry.getKey();
                    int bitCount = entry.getValue();
                    int reviewCount = couponReviewRepository.countCouponReviewByCoupon(coupon);
                    double avgRating = Optional.ofNullable(
                            couponReviewRepository.getAverageRatingByCoupon(coupon)).orElse(0.0);
                    double bayesScore = computeBayesianAverage(avgRating, reviewCount, globalCouponAvg, MIN_REVIEWS);
                    return new ProductWithScore<>(coupon, bitCount, bayesScore);
                })
                .sorted((c1, c2) -> Double.compare(c2.bayesScore, c1.bayesScore))
                .limit(5)
                .toList();

        List<CouponDetailResponseDto> recommendedCoupons = topCoupons.stream()
                .map(p -> {
                    List<String> tags = tagMetaService.extractTagsFromCode(p.product.getTagCode());
                    String category = commonCodeService.getCodeName("G04", p.product.getCategoryCode());
                    return CouponDetailResponseDto.from(p.product, tags, category);
                })
                .toList();

        System.out.println("🔽 최종 추천된 쿠폰 5개:");
        for (ProductWithScore<Coupon> p : topCoupons) {
            System.out.printf("- couponId: %d, name: %s, bitCount: %d, bayesScore: %.2f%n",
                    p.product.getCouponId(), p.product.getCouponName(), p.bitCount, p.bayesScore);
        }

        return new RecommendCouponResponseDto(recommendedCoupons);
    }



    // 출력용 클래스
    private static class ProductWithScore<T> {
        T product;
        int bitCount;
        double bayesScore;

        public ProductWithScore(T product, int bitCount, double bayesScore) {
            this.product = product;
            this.bitCount = bitCount;
            this.bayesScore = bayesScore;
        }

        public T getProduct() {
            return product;
        }

        public int getBitCount() {
            return bitCount;
        }

        public double getBayesScore() {
            return bayesScore;
        }
    }




}
