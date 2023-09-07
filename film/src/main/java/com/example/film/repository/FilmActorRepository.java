package com.example.film.repository;

import com.example.film.domain.Film;
import com.example.film.domain.FilmActor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmActorRepository extends JpaRepository<FilmActor, Long> {
}
