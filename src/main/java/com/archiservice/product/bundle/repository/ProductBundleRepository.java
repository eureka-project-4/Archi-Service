package com.archiservice.product.bundle.repository;

import com.archiservice.product.bundle.domain.ProductBundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBundleRepository extends JpaRepository<ProductBundle, Long>{
}
