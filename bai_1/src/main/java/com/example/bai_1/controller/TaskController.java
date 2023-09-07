package com.example.bai_1.controller;


import com.example.bai_1.domain.Task;
import com.example.bai_1.domain.enumration.TaskStatus;
import com.example.bai_1.domain.enumration.TaskType;
import com.example.bai_1.service.task.TaskService;
import com.example.bai_1.service.task.request.TaskSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/task")
@AllArgsConstructor
public class TaskController {


    private final TaskService taskService;

    @GetMapping
    public ModelAndView showListTasks(@RequestParam(required = false) String message) {
        ModelAndView view = new ModelAndView("task/index");
        view.addObject("tasks", taskService.getTasks());
        view.addObject("message", message);
        view.addObject("statuses", TaskStatus.values());
        return view;
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {
        ModelAndView view = new ModelAndView("task/create");
        view.addObject("task", new TaskSaveRequest());
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }

    @PostMapping("/create")
    public ModelAndView showCreate(@ModelAttribute TaskSaveRequest task) {
        ModelAndView view = new ModelAndView("task/create");
        taskService.create(task);
        view.addObject("message", "Created");
        view.addObject("task", new TaskSaveRequest());
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }
    @GetMapping("/{id}/{status}")
    public String changeStatus(@PathVariable Long id, @PathVariable TaskStatus status){
        taskService.changeStatus(id, status);
        return "redirect:/task?message=Change Success";
    }


    @PostMapping("/create")
    public String create (@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:/task";
    }
}