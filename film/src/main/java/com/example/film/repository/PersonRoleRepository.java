package com.example.film.repository;

import com.example.film.domain.FilmActor;
import com.example.film.domain.PersonRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRoleRepository extends JpaRepository<PersonRole, Long> {
}
