package com.example.dailytask.Domain;


import com.example.dailytask.Domain.Enumration.TaskStatus;
import com.example.dailytask.Domain.Enumration.TaskType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task_history")
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