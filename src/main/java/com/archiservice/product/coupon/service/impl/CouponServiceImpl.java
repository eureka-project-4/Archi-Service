package com.archiservice.product.coupon.service.impl;

import com.archiservice.code.commoncode.service.CommonCodeService;
import com.archiservice.code.tagmeta.service.TagMetaService;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.product.coupon.domain.Coupon;
import com.archiservice.product.coupon.dto.response.CouponDetailResponseDto;
import com.archiservice.product.coupon.dto.response.CouponResponseDto;
import com.archiservice.product.coupon.repository.CouponRepository;
import com.archiservice.product.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponServiceImpl implements CouponService {

    public static final String CATEGORY_GROUP_CODE = "G04";
    private final CouponRepository couponRepository;
    private final TagMetaService tagMetaService;
    private final CommonCodeService commonCodeService;

    @Override
    public Page<CouponResponseDto> getAllCoupons(Pageable pageable) {
        Page<Coupon> couponPage = couponRepository.findAll(pageable);

        return couponPage.map(coupon -> {
            List<String> tags = tagMetaService.extractTagsFromCode(coupon.getTagCode());
            String category = commonCodeService.getCodeName(CATEGORY_GROUP_CODE, coupon.getCategoryCode());
            return CouponResponseDto.from(coupon, tags, category);
        });
    }

    @Override
    public CouponDetailResponseDto getCouponDetail(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        List<String> tags = tagMetaService.extractTagsFromCode(coupon.getTagCode());
        String category = commonCodeService.getCodeName(CATEGORY_GROUP_CODE, coupon.getCategoryCode());

        return CouponDetailResponseDto.from(coupon, tags, category);
    }
}

