package com.example.film.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate publishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Person director;

    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY)
    private List<FilmActor> filmActors;

    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY)
    private List<FilmCategory> filmCategories;
}
