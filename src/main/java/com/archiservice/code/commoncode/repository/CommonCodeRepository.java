package com.archiservice.code.commoncode.repository;

import com.archiservice.code.commoncode.domain.CommonCode;
import com.archiservice.code.commoncode.domain.id.CommonCodeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeId> {
    List<CommonCode> findByGroupCode(String groupCode);
    Optional<CommonCode> findByGroupCodeAndCommonCode(String groupCode, String commonCode);
}
