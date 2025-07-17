package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dto.TaskDTO;
import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.entity.User;

import java.util.List;

public interface TaskService {

    void removeAll();

    List<Task> findAll();
    List<Task> findAllByProjectId(Long projectId);
    List<Task> findAllAssignedToUser(User user);
    List<Task> findAllCreatedByUser(User user);
    List<Task> findAllInProgressByUser(User user);

    List<Requirement> findAllRequirementsAssignedToUser(User user);
    List<Requirement> findAllRequirementsCreatedByUser(User user);
    List<Requirement> findAllRequirementsInProgressByUser(User user);

    Task findById(Long taskId);

    Requirement findRequirementById(Long requirementId);

    void save(Task task);

    void saveRequirement(Requirement requirement);

    void deleteById(Long taskId);

    void deleteRequirementById(Long requirementId);

    Task mapToTask(TaskDTO taskDTO);
    TaskDTO mapToDTO(Task task);

}
