package com.example.demo.controller;




import com.example.demo.service.UserService;
import com.example.demo.service.auth.request.LoginRequest;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/api")
public class HelloController {

    private final UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{message}")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView helloWorld(@PathVariable String message){
        ModelAndView view = new ModelAndView("index");
        view.addObject("login", new LoginRequest());
        view.addObject("message", message);
        return view;// req.getDispatcher('index').forward(req,resp)
    }
    @PostMapping("/hello")
    public ModelAndView demo(@ModelAttribute LoginRequest login){
        ModelAndView view = new ModelAndView("hello");
        view.addObject("login", login);
        return view;
    }


}