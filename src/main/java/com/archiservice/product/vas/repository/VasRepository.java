package com.archiservice.product.vas.repository;

import com.archiservice.product.vas.domain.Vas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VasRepository extends JpaRepository<Vas, Long> {
}
