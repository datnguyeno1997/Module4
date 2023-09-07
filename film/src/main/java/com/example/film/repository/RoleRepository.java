package com.example.film.repository;

import com.example.film.domain.FilmActor;
import com.example.film.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
