package com.example.film.service.person;

import com.example.film.repository.PersonRepository;
import com.example.film.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;


    public List<SelectOptionResponse> getPersonsByRole(String role){
        //list Person => list SelectOptionResponse.
        return personRepository.getPersonsByRole(role)
                .stream()
                .map(element -> new SelectOptionResponse(element.getId().toString(), element.getName()))
                .collect(Collectors.toList());
    }
}
