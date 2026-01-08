package com.example.watch.repository;

import com.example.watch.entity.Product;
import com.example.watch.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository
        extends JpaRepository<Product, Long>,
                JpaSpecificationExecutor<Product> {

    /* ================= BASIC ================= */

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.brand
        LEFT JOIN FETCH p.category
        WHERE p.status = :status
    """)
    List<Product> findByStatus(@Param("status") ProductStatus status);

    /* ================= READ ALL ================= */

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.brand
        LEFT JOIN FETCH p.category
    """)
    List<Product> findAllWithRelations();

    /* ================= READ BY ID ================= */

    @Query("""
        SELECT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.brand
        LEFT JOIN FETCH p.category
        WHERE p.id = :id
    """)
    Optional<Product> findDetailById(@Param("id") Long id);

    /* ================= FILTER ================= */

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.brand
        LEFT JOIN FETCH p.category
        WHERE p.category.id = :categoryId
    """)
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.brand
        LEFT JOIN FETCH p.category
        WHERE p.brand.id = :brandId
    """)
    List<Product> findByBrandId(@Param("brandId") Long brandId);

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.brand
        LEFT JOIN FETCH p.category
        WHERE p.category.id = :categoryId
          AND p.brand.id = :brandId
    """)
    List<Product> findByCategoryIdAndBrandId(
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId
    );

    /* ================= SEARCH ================= */

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.brand
        LEFT JOIN FETCH p.category
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Product> findByNameContainingIgnoreCase(@Param("keyword") String keyword);

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.brand
        LEFT JOIN FETCH p.category
        WHERE p.category.id = :categoryId
          AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Product> findByCategoryIdAndNameContainingIgnoreCase(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword
    );

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.brand
        LEFT JOIN FETCH p.category
        WHERE p.brand.id = :brandId
          AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Product> findByBrandIdAndNameContainingIgnoreCase(
            @Param("brandId") Long brandId,
            @Param("keyword") String keyword
    );

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.brand
        LEFT JOIN FETCH p.category
        WHERE p.category.id = :categoryId
          AND p.brand.id = :brandId
          AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Product> findByCategoryIdAndBrandIdAndNameContainingIgnoreCase(
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            @Param("keyword") String keyword
    );
}
