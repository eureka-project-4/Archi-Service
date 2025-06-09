package com.archiservice.code.commoncode.domain.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommonCodeId implements Serializable {

    @Column(name = "common_code", length = 3)
    private String commonCode;

    @Column(name = "group_code", length = 3)
    private String groupCode;
}
