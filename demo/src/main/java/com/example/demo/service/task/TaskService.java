package com.example.demo.service.task;

import com.example.demo.model.Task;
import com.example.demo.model.enumration.TaskStatus;
import com.example.demo.model.enumration.TaskType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private static long currentId = 1L;
    private final static List<Task> tasks;

    static {
        tasks = new ArrayList<>();
        var task1 = Task.builder().id(1L).title("Hoc Java")
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusHours(2L))
                .type(TaskType.DAILY)
                .status(TaskStatus.IN_PROGRESS).build();
        var task2 = Task.builder().id(2L).title("Hoc Ky nang viet CV")
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusHours(2L))
                .type(TaskType.DAILY)
                .status(TaskStatus.IN_PROGRESS).build();
        tasks.add(task1);
        tasks.add(task2);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task findById(Long id) {
        Task task1;
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public void save(Task task) {
        task.setId(currentId++);
        tasks.add(task);
    }

    public void edit(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(task.getId())) {
                tasks.set(i, task);
                return;
            }
        }
    }

    public void delete(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }
}