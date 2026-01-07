package com.example.watch.service.impl;

import com.example.watch.dto.request.BrandRequestDTO;
import com.example.watch.dto.response.BrandResponseDTO;
import com.example.watch.entity.Brand;
import com.example.watch.mapper.BrandMapper;
import com.example.watch.repository.BrandRepository;
import com.example.watch.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public BrandResponseDTO create(BrandRequestDTO dto) {
        Brand brand = BrandMapper.toEntity(dto);
        return BrandMapper.toDTO(brandRepository.save(brand));
    }

    @Override
    public List<BrandResponseDTO> getAll() {
        return brandRepository.findAll()
                .stream()
                .map(BrandMapper::toDTO)
                .toList();
    }

    @Override
    public BrandResponseDTO getById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        return BrandMapper.toDTO(brand);
    }

    @Override
    public BrandResponseDTO update(Long id, BrandRequestDTO dto) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        brand.setName(dto.getName());
        return BrandMapper.toDTO(brandRepository.save(brand));
    }

    @Override
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }
}

