package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dto.TaskDTO;
import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();
    List<Task> findAllByProjectId(Long projectId);
    List<Task> findAllInProgressByUser(Long userId);

    Task findById(Long taskId);

    Requirement findRequirementById(Long requirementId);

    void save(Task task);

    void saveRequirement(Requirement requirement);

    void deleteById(Long taskId);

    void deleteRequirementById(Long requirementId);

    Task mapToTask(TaskDTO taskDTO);
    TaskDTO mapToDTO(Task task);

}
