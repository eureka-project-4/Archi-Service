package com.archiservice.user.repository;

import com.archiservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // username으로 (entity) User 조회 - 로그인 후 사용
    Optional<User> findByUsername(String username);

    // email로 (entity) User 조회 - 로그인시 사용
    Optional<User> findByEmail(String email);

    // username 존재 여부 확인 - 중복 체크용
    boolean existsByUsername(String username);

    // email 존재 여부 확인 - 중복 체크용
    boolean existsByEmail(String email);
}
