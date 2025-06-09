package com.archiservice.code.commoncode.domain;

import com.archiservice.code.commoncode.domain.id.CommonCodeId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "common_code")
@IdClass(CommonCodeId.class)
@Getter
@NoArgsConstructor
public class CommonCode {

    @EmbeddedId
    private CommonCodeId commonCodeId;

    @Column(name = "common_name", length = 100)
    private String commonName;
}


