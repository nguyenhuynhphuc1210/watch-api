package com.example.watch.service.impl;

import com.example.watch.entity.Product;
import com.example.watch.entity.ProductImage;
import com.example.watch.repository.ProductImageRepository;
import com.example.watch.repository.ProductRepository;
import com.example.watch.service.CloudinaryService;
import com.example.watch.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public void uploadImages(
            Long productId,
            List<MultipartFile> files,
            boolean setPrimary
    ) {

        Product product = productRepository.findDetailById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        boolean hasPrimary = product.getImages()
                .stream()
                .anyMatch(ProductImage::getIsPrimary);

        for (int i = 0; i < files.size(); i++) {

            String imageUrl = cloudinaryService.uploadImage(files.get(i));

            ProductImage image = new ProductImage();
            image.setProduct(product);
            image.setImageUrl(imageUrl);

            if (setPrimary && !hasPrimary && i == 0) {
                image.setIsPrimary(true);
                hasPrimary = true;
            }

            product.getImages().add(image);
            productImageRepository.save(image);
        }
    }
}
