package com.example.watch.service;

import com.example.watch.dto.request.BrandRequestDTO;
import com.example.watch.dto.response.BrandResponseDTO;
import java.util.List;

public interface BrandService {
    BrandResponseDTO create(BrandRequestDTO dto);
    List<BrandResponseDTO> getAll();
    BrandResponseDTO getById(Long id);
    BrandResponseDTO update(Long id, BrandRequestDTO dto);
    void delete(Long id);
}

