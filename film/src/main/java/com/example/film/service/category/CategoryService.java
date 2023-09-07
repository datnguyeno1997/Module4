package com.example.film.service.category;

import com.example.film.repository.CategoryRepository;
import com.example.film.repository.PersonRepository;
import com.example.film.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;


    public List<SelectOptionResponse> getPersonForSelect(){
        return repository.findAll()
                .stream()
                .map(element -> new SelectOptionResponse(element.getId().toString(), element.getName()))
                .collect(Collectors.toList());
    }
}
