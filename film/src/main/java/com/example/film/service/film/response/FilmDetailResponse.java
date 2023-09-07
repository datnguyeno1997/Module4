package com.example.film.service.film.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class FilmDetailResponse {

    private Long id;

    private String name;

    private LocalDate publishDate;

    private List<Long> actorsId;

    private List<Long> categoriesId;

    private Long directorId;

}
