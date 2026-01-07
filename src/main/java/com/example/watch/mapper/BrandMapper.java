package com.example.watch.mapper;

import com.example.watch.dto.request.BrandRequestDTO;
import com.example.watch.dto.response.BrandResponseDTO;
import com.example.watch.entity.Brand;

public class BrandMapper {

    public static BrandResponseDTO toDTO(Brand brand) {
        return new BrandResponseDTO(
                brand.getId(),
                brand.getName(),
                brand.getCountry(),
                brand.getDescription(),
                brand.getLogoUrl()
        );
    }

    public static Brand toEntity(BrandRequestDTO dto) {
        Brand brand = new Brand();
        brand.setName(dto.getName());
        brand.setCountry(dto.getCountry());
        brand.setDescription(dto.getDescription());
        brand.setLogoUrl(dto.getLogoUrl());
        return brand;
    }
}
