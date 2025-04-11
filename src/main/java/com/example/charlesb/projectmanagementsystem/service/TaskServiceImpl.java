package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dao.RequirementRepository;
import com.example.charlesb.projectmanagementsystem.dao.TaskRepository;
import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final RequirementRepository requirementRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, RequirementRepository requirementRepository) {
        this.taskRepository = taskRepository;
        this.requirementRepository = requirementRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findAllByProjectId(Long projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }

    @Override
    public Task findById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public Requirement findRequirementById(Long requirementId) {
        return requirementRepository.findById(requirementId).orElse(null);
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void saveRequirement(Requirement requirement) {
        requirementRepository.save(requirement);
    }

    @Override
    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void deleteRequirementById(Long requirementId) {
        requirementRepository.deleteById(requirementId);
    }

}
