package com.example.demo.controller;

import com.example.demo.service.task.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.model.Task;
import com.example.demo.model.enumration.TaskStatus;
import com.example.demo.model.enumration.TaskType;

@Controller
@RequestMapping(value = "/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ModelAndView showListTasks(){
        ModelAndView view = new ModelAndView("task/index");
        view.addObject("tasks", taskService.getTasks());
        return view;
    }

    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView view = new ModelAndView("task/create");
        view.addObject("task", new Task());
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEdit( Long id){
        Task task = taskService.findById(id);
        ModelAndView view = new ModelAndView("task/edit");

        view.addObject("task", task);
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }

//    @GetMapping("/delete/{id}")
//    public ModelAndView showDelete(@PathVariable Long id) {
//        Task task = taskService.findById(id);
//        if (task == null) {
//            // Xử lý nếu không tìm thấy công việc với id tương ứng, ví dụ: redirect hoặc hiển thị lỗi.
//        }
//
//        ModelAndView view = new ModelAndView("task/delete");
//        view.addObject("task", task);
//        return view;
//    }

    @PostMapping("/create")
    public String create (@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:/task";
    }
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute Task task) {
        taskService.edit(task);
        return "redirect:/task";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Task task) {
        taskService.delete(task.getId());
        return "redirect:/task";
    }

}