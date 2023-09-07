package com.example.film.repository;

import com.example.film.domain.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FilmRepository extends JpaRepository<Film, Long> {

    //Page<Film> findFilmByDirector_NameOrName(String director_name, String name, Pageable pageable);
    @Query(value = "SELECT f FROM Film f " +
            " where f.name LIKE :search OR  f.director.name LIKE :search " +
            " OR EXISTS ( " +
            "  SELECT 1 FROM FilmActor a " +
            "  WHERE a.film = f " +
            "  AND a.actor.name LIKE :search " +
            ") " +
            " OR EXISTS ( " +
            "  SELECT 1 FROM FilmCategory c " +
            "  WHERE c.film = f " +
            "  AND c.category.name LIKE :search )"
    )
    Page<Film> searchEverythingIgnorePublishDate(String search, Pageable pageable);
}
