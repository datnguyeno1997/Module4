package com.example.bai_1.domain;

import com.example.bai_1.domain.enumration.TaskStatus;
import com.example.bai_1.domain.enumration.TaskType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalTime start;

    private LocalTime end;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // để xác định được OneToMany hay ManyToOne thì phải câu hỏi Task History thằng 1 nào thằng nhiều
    // chữ đầu tiên đại diện cho class đang đứng
    // chữ thứ 2 đại diện cho field bên dưới
    @OneToMany(mappedBy = "task") // task ở đây được lấy name field task của TaskHistory
    private Set<TaskHistory> taskHistories;


}