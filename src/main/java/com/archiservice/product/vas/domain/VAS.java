package com.archiservice.product.vas.domain;

import com.archiservice.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "services")
public class VAS extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "sale_rate")
    private Integer saleRate;

    @Column(name = "tag_code")
    private Long tagCode;

    @Column(name = "category_code", length = 3)
    private String categoryCode;

    public Integer getDiscountedPrice() {
        if (saleRate == null || saleRate == 0) {
            return price;
        }
        return price - (price * saleRate / 100);
    }

    public boolean isOnSale() {
        return saleRate != null && saleRate > 0;
    }
}
