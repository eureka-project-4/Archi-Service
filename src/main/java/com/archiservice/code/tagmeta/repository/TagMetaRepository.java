package com.archiservice.code.tagmeta.repository;

import com.archiservice.code.tagmeta.domain.TagMeta;
import com.archiservice.code.tagmeta.domain.key.TagMetaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagMetaRepository extends JpaRepository<TagMeta, TagMetaId> {
    List<TagMeta> findByBitPositionIn(List<Integer> bitPositions);
}
