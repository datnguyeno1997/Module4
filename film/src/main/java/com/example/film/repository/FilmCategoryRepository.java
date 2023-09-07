package com.example.film.repository;

import com.example.film.domain.Category;
import com.example.film.domain.FilmCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmCategoryRepository extends JpaRepository<FilmCategory, Long> {
}
