package com.example.bai_1.repository;


import com.example.bai_1.domain.TaskHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {

    @Query(value = "SELECT t  FROM TaskHistory t WHERE  " +
            "(DATE_FORMAT(t.start, '%Y-%m-%d') <= DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') AND " +
            "DATE_FORMAT(t.end, '%Y-%m-%d') >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d'))" +
            "ORDER BY t.start" )
    List<TaskHistory> findAllTaskToDay();
}