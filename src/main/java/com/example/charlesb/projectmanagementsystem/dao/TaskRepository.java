package com.example.charlesb.projectmanagementsystem.dao;

import com.example.charlesb.projectmanagementsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByParentTaskId(Long parentTaskId);

}
