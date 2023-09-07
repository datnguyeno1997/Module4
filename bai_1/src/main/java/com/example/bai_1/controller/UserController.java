package com.example.bai_1.controller;

import com.example.bai_1.domain.enumration.EGender;
import com.example.bai_1.service.user.UserService;
import com.example.bai_1.service.user.request.UserEditRequest;
import com.example.bai_1.service.user.request.UserSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView view = new ModelAndView("user/create");
        view.addObject("user", new UserSaveRequest());
        view.addObject("genders", EGender.values());
        return view;
    }

    @PostMapping("/create")
    public ModelAndView showCreate(@ModelAttribute UserSaveRequest user){
        ModelAndView view = new ModelAndView("user/create");
        userService.create(user);
        view.addObject("message", "Created");
        view.addObject("user", new UserSaveRequest());
        view.addObject("genders", EGender.values());
        return view;
    }

    @GetMapping
    public ModelAndView showList(@RequestParam(required = false) String message){
        ModelAndView view = new ModelAndView("user/index");
        view.addObject("message",message);
        view.addObject("users", userService.findAll());
        return  view;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        userService.deleteById(id);
        return "redirect:/user?message=Deleted";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id){
        ModelAndView view = new ModelAndView("user/edit");
        view.addObject("user", userService.showEditById(id));
        view.addObject("genders", EGender.values());
        return view;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute UserEditRequest user,
                             @PathVariable Long id){
        ModelAndView view = new ModelAndView("user/edit");
        try{
            userService.edit(user, id);
        }catch (Exception e){
            view.addObject("message", e.getMessage());
            view.addObject("user", user);
            view.addObject("genders", EGender.values());
            return view;
        }
        view.addObject("message", "Edited");
        view.addObject("user", user);
        view.addObject("genders", EGender.values());
        return view;
    }

}