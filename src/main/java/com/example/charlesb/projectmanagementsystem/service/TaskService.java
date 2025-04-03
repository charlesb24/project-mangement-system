package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();
    List<Task> findAllByProjectId(Long projectId);

    Task findById(Long taskId);

    void save(Task task);

    void deleteById(Long taskId);

}
