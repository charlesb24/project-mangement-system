package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    Task findById(Long taskId);

    List<Task> findChildTasks(Long taskId);
    List<Task> findChildTasks(Task parentTask);

    Task findParentTask(Long childTaskId);
    Task findParentTask(Task childTask);

    void save(Task task);

    void deleteById(Long taskId);

}
