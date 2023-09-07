package com.example.bai_1.domain;


import com.example.bai_1.domain.enumration.TaskStatus;
import com.example.bai_1.domain.enumration.TaskType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task_histories")
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime start;

    private LocalDateTime end;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @Enumerated(value = EnumType.STRING)
    private TaskType type;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @PrePersist
    public void setupBeforeInsert(){
        status = TaskStatus.TODO;
    }
}