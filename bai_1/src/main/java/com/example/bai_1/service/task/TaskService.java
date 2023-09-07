package com.example.bai_1.service.task;


import com.example.bai_1.domain.Task;
import com.example.bai_1.domain.TaskHistory;
import com.example.bai_1.domain.enumration.TaskStatus;
import com.example.bai_1.domain.enumration.TaskType;
import com.example.bai_1.exception.ResourceNotFoundException;
import com.example.bai_1.repository.TaskHistoryRepository;
import com.example.bai_1.repository.TaskRepository;
import com.example.bai_1.service.task.request.TaskSaveRequest;
import com.example.bai_1.service.task.response.TaskListResponse;
import com.example.bai_1.util.AppMessage;
import com.example.bai_1.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {
    private static long currentId = 1L;


    private final TaskRepository taskRepository;

    private final TaskHistoryRepository taskHistoryRepository;

    public List<TaskListResponse> getTasks() {

        return taskHistoryRepository.findAllTaskToDay()
                .stream()
                .map(e -> AppUtil.mapper.map(e, TaskListResponse.class))
                .collect(Collectors.toList());
    }

    public void create(TaskSaveRequest request) {
        var taskHistory = AppUtil.mapper.map(request, TaskHistory.class);
        if (Objects.equals(request.getType(), TaskType.DAILY.toString())) {
            var task = AppUtil.mapper.map(request, Task.class);
            task = taskRepository.save(task);
            //create new task history for display in home screen
            LocalDate now = LocalDateTime.now().toLocalDate();
            taskHistory.setTask(task);
            taskHistory.setStart(LocalDateTime.of(now, task.getStart()));
            taskHistory.setEnd(LocalDateTime.of(now, task.getEnd()));
        }

        taskHistoryRepository.save(taskHistory);
    }

    public void changeStatus(Long id, TaskStatus status){
        var task = findById(id);
        task.setStatus(status);
        taskHistoryRepository.save(task);
    }

    public TaskHistory findById(Long id){
        return taskHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(AppMessage.ID_NOT_FOUND, "Task", id)));
    }

//    public void save(Task task) {
//        task.setId(currentId++);
//        tasks.add(task);
//    }
}