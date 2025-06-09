package com.archiservice.code.tagmeta.domain.id;

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
public class TagMetaId implements Serializable {

    @Column(name = "tag_type", length = 20)
    private String tagType;

    @Column(name = "tag_key", length = 50)
    private String tagKey;
}

