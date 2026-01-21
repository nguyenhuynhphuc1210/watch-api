package com.example.watch.service;

import com.example.watch.assembler.ProductModelAssembler;
import com.example.watch.dto.request.ProductRequestDTO;
import com.example.watch.dto.response.ProductResponseDTO;
import com.example.watch.entity.Brand;
import com.example.watch.entity.Category;
import com.example.watch.entity.Product;
import com.example.watch.repository.BrandRepository;
import com.example.watch.repository.CategoryRepository;
import com.example.watch.repository.ProductRepository;
import com.example.watch.service.impl.ProductServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductModelAssembler assembler;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Brand brand;
    private Category category;
    private ProductRequestDTO requestDTO;
    private ProductResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setId(1L);
        brand.setName("Rolex");

        category = new Category();
        category.setId(1L);
        category.setName("Luxury");

        product = new Product();
        product.setId(1L);
        product.setName("Rolex Submariner");
        product.setPrice(BigDecimal.valueOf(1000));

        requestDTO = new ProductRequestDTO();
        requestDTO.setName("Rolex Submariner");
        requestDTO.setBrandId(1L);
        requestDTO.setCategoryId(1L);

        responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Rolex Submariner");
    }

    // ================= CREATE =================
    @Test
    void create_success() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(assembler.toModel(any())).thenReturn(responseDTO);

        ProductResponseDTO result = productService.create(requestDTO);

        assertNotNull(result);
        assertEquals("Rolex Submariner", result.getName());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void create_brandNotFound_throwException() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> productService.create(requestDTO)
        );

        assertEquals("Brand not found", ex.getMessage());
    }

    // ================= GET ALL =================
    @Test
    void getAll_success() {
        when(productRepository.findAllWithRelations())
                .thenReturn(List.of(product));
        when(assembler.toModel(any())).thenReturn(responseDTO);

        List<ProductResponseDTO> result = productService.getAll();

        assertEquals(1, result.size());
        verify(productRepository).findAllWithRelations();
    }

    // ================= GET BY ID =================
    @Test
    void getById_success() {
        when(productRepository.findDetailById(1L))
                .thenReturn(Optional.of(product));
        when(assembler.toModel(any())).thenReturn(responseDTO);

        ProductResponseDTO result = productService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getById_notFound_throwException() {
        when(productRepository.findDetailById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> productService.getById(1L)
        );

        assertEquals("Product not found", ex.getMessage());
    }

    // ================= UPDATE =================
    @Test
    void update_success() {
        when(productRepository.findDetailById(1L))
                .thenReturn(Optional.of(product));
        when(brandRepository.findById(1L))
                .thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class)))
                .thenReturn(product);
        when(assembler.toModel(any()))
                .thenReturn(responseDTO);

        ProductResponseDTO result = productService.update(1L, requestDTO);

        assertNotNull(result);
        verify(productRepository).save(product);
    }

    // ================= DELETE =================
    @Test
    void delete_success() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.delete(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void delete_notFound_throwException() {
        when(productRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> productService.delete(1L)
        );

        assertEquals("Product not found", ex.getMessage());
    }
}
