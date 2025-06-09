package com.archiservice.product.plan.domain;

import com.archiservice.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "plans")
public class Plan extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "month_data")
    private Integer monthData;

    @Column(name = "call_usage")
    private String callUsage;

    @Column(name = "message_usage")
    private String messageUsage;

    @Column(name = "benefit")
    private String benefit;

    @Column(name = "tag_code")
    private Long tagCode;

    @Column(name = "age_code", length = 3)
    private String ageCode;

    @Column(name = "category_code", length = 3)
    private String categoryCode;
}
