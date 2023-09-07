package com.example.dailytask.Service.Task;

import com.example.dailytask.Domain.Enumration.TaskStatus;
import com.example.dailytask.Domain.Enumration.TaskType;
import com.example.dailytask.Domain.Task;
import com.example.dailytask.Domain.TaskHistory;
import com.example.dailytask.Exception.ResourceNotFoundException;
import com.example.dailytask.Exception.TaskNotFoundException;
import com.example.dailytask.Repository.TaskHistoryRepository;
import com.example.dailytask.Repository.TaskRepository;
import com.example.dailytask.Service.Task.Request.TaskEditRequest;
import com.example.dailytask.Service.Task.Request.TaskSaveRequest;
import com.example.dailytask.Service.Task.Response.TaskListResponse;
import com.example.dailytask.Util.AppMessage;
import com.example.dailytask.Util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskHistoryRepository taskHistoryRepository;

    public List<TaskListResponse> getTasks() {
        return taskRepository.findAll()
                .stream()
                .map(e -> AppUtil.mapper.map(e, TaskListResponse.class))
                .collect(Collectors.toList());
    }

    public void create(TaskSaveRequest request){
        var taskHistory = AppUtil.mapper.map(request, TaskHistory.class);
        if (Objects.equals(request.getType(), TaskType.DAILY.toString())){
            var task = AppUtil.mapper.map(request, Task.class);
            task = taskRepository.save(task);

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

    public Task findByID(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "Task", id)));
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent()) {
            Task existingTask = taskOptional.get();
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStart(updatedTask.getStart());
            existingTask.setEnd(updatedTask.getEnd());

            return taskRepository.save(existingTask);

        } else {
            return null;
        }
    }

    public TaskEditRequest showEditById(Long id){
        Task task = getTaskById(id);
        return taskToTaskEditRequest(task);
    }
    public void edit(TaskEditRequest request, Long id) throws Exception{
        var taskInDb = getTaskById(id);

        taskInDb.setStart(AppUtil.mapper.map(request.getStart(), LocalTime.class));
        taskInDb.setEnd(AppUtil.mapper.map(request.getEnd(), LocalTime.class));
        taskInDb.setType(TaskType.valueOf(request.getType()));
        taskInDb.setTitle(request.getTitle());
        taskInDb.setDescription(request.getDescription());
        request.setId(id.toString());
        taskRepository.save(taskInDb);
    }

    private TaskEditRequest taskToTaskEditRequest(Task task){
        var result = new TaskEditRequest();
        result.setTitle(String.valueOf(task.getTitle()));
        result.setDescription(String.valueOf(task.getDescription()));
        result.setStart(String.valueOf(task.getStart()));
        result.setEnd(String.valueOf(task.getEnd()));
        result.setType(String.valueOf(task.getType()));
        result.setId(String.valueOf(task.getId()));
        return result;
    }

//    List<Task> findByStartBetween(LocalDateTime startDate, LocalDateTime endDate);
//
//    public List<TaskListResponse> getTasksByMonth(int year, int month) {
//        LocalDate startDate = LocalDate.of(year, month, 1);
//        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
//
//        return taskRepository.findByStartBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX))
//                .stream()
//                .map(e -> AppUtil.mapper.map(e, TaskListResponse.class))
//                .collect(Collectors.toList());
//    }
//
//    public List<TaskListResponse> getTasksByWeek(int year, int week) {
//        LocalDate startDate = LocalDate.ofYearDay(year, 1).with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week);
//        LocalDate endDate = startDate.plusDays(6);
//
//        return taskRepository.findByStartBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX))
//                .stream()
//                .map(e -> AppUtil.mapper.map(e, TaskListResponse.class))
//                .collect(Collectors.toList());
//    }

}