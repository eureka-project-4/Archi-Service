package com.archiservice.tagmeta.domain;

import com.archiservice.common.TimeStamp;
import com.archiservice.tagmeta.domain.key.TagMetaId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tag_meta")
public class TagMeta extends TimeStamp {

    @EmbeddedId
    private TagMetaId id;

    @Column(name = "tag_description", length = 50)
    private String tagDescription;

    @Column(name = "bit_position")
    private Integer bitPosition;
}
