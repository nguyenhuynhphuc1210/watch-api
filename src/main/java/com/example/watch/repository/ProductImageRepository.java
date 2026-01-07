package com.example.watch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.watch.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProduct_Id(Long productId);
}

