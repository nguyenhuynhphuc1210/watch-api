package com.example.watch.controller;

import com.example.watch.dto.request.BrandRequestDTO;
import com.example.watch.dto.response.BrandResponseDTO;
import com.example.watch.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<BrandResponseDTO> create(
            @RequestBody BrandRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(brandService.create(dto));
    }

    // ================= GET ALL =================
    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAll() {
        return ResponseEntity.ok(brandService.getAll());
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(brandService.getById(id));
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> update(
            @PathVariable Long id,
            @RequestBody BrandRequestDTO dto) {
        return ResponseEntity.ok(brandService.update(id, dto));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/logo")
    public BrandResponseDTO uploadLogo(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return brandService.uploadLogo(id, file);
    }
}
