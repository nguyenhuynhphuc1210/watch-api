package com.example.watch.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProductImageService {
    void uploadImages(Long productId, List<MultipartFile> files, boolean setPrimary);
}
