package com.archiservice.product.coupon.domain;

import com.archiservice.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupons")
public class Coupon extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "tag_code")
    private Long tagCode;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "category_code", length = 3)
    private String categoryCode;

    public int getPrice(){
        if(price == null) return 0;
        else return price;
    }
}

