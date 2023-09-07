package com.example.bai_1.service.task.response;

import com.example.bai_1.domain.enumration.TaskStatus;
import com.example.bai_1.domain.enumration.TaskType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class TaskListResponse {
    private Long id;

    private String title;

    private String description;

    private LocalTime start;

    private LocalTime end;

    private TaskStatus status;

    private TaskType type;

    public String getTime(){
        return start.toString() + " - " + end;
    }
}