package com.example.watch.controller;

import com.example.watch.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping
    public void uploadImages(
            @PathVariable Long productId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(defaultValue = "false") boolean setPrimary
    ) {
        productImageService.uploadImages(productId, files, setPrimary);
    }
}
