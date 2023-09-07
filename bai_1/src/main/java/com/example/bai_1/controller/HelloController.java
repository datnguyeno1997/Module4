package com.example.bai_1.controller;

import com.example.bai_1.domain.Task;
import com.example.bai_1.repository.TaskRepository;
import com.example.bai_1.service.auth.request.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class HelloController {


    private final TaskRepository taskRepository;

    public HelloController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/{message}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Task> helloWorld(@PathVariable String message){
        ModelAndView view = new ModelAndView("index");
        view.addObject("login", new LoginRequest());
        view.addObject("message", message);
        return taskRepository.findAll();// req.getDispatcher('index').forward(req,resp)
    }
    @PostMapping("/hello")
    public ModelAndView demo(@ModelAttribute LoginRequest login){
        ModelAndView view = new ModelAndView("hello");
        view.addObject("login", login);
        return view;
    }


}