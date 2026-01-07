package com.example.watch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.watch.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

