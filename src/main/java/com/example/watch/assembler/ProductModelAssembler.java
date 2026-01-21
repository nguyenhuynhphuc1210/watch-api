package com.example.watch.assembler;

import com.example.watch.controller.ProductController;
import com.example.watch.dto.response.ProductResponseDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductModelAssembler
        implements RepresentationModelAssembler<ProductResponseDTO, ProductResponseDTO> {

    @Override
    public ProductResponseDTO toModel(ProductResponseDTO dto) {

        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ProductController.class)
                                .getById(dto.getId())
                ).withSelfRel()
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ProductController.class)
                                .getAll(null, null, null)
                ).withRel("list").expand()
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ProductController.class)
                                .update(dto.getId(), null)
                ).withRel("update")
        );

        dto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ProductController.class)
                                .delete(dto.getId())
                ).withRel("delete")
        );

        return dto;
    }
}
