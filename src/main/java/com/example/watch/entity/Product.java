package com.example.watch.entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.watch.enums.Gender;
import com.example.watch.enums.ProductStatus;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false) // Một chiếc đồng hồ phải có thương hiệu
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false) // Phải thuộc một danh mục để dễ tìm kiếm
    private Category category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity = 0; // Nên gán mặc định là 0

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String specification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // Bắt buộc để phân loại giới tính
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // Mặc định là ACTIVE hoặc DRAFT
    private ProductStatus status = ProductStatus.ACTIVE;

    @OneToMany(
        mappedBy = "product",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<ProductImage> images = new ArrayList<>();
}



