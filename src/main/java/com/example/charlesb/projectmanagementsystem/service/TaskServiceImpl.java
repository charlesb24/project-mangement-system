package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dao.RequirementRepository;
import com.example.charlesb.projectmanagementsystem.dao.TaskRepository;
import com.example.charlesb.projectmanagementsystem.dao.UserRepository;
import com.example.charlesb.projectmanagementsystem.dto.TaskDTO;
import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.helper.ConversionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final RequirementRepository requirementRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, RequirementRepository requirementRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.requirementRepository = requirementRepository;
        this.userRepository = userRepository;
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

    @Override
    public Task mapToTask(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }

        Task task = taskRepository.findById(taskDTO.getId()).orElse(new Task());
        Optional<User> foundUser = userRepository.findById(taskDTO.getAssignedToUserId());

        if (task.getAssignedTo() == null || task.getAssignedTo().getId() != taskDTO.getAssignedToUserId()) {
            if (foundUser.isPresent()) {
                task.setAssignedTo(foundUser.get());
            }
        }

        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(ConversionHelper.intToStatus(taskDTO.getStatus()));

        return task;
    }

    @Override
    public TaskDTO mapToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getTaskId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setPriority(task.getPriority());
        taskDTO.setStatus(ConversionHelper.statusToInt(task.getStatus()));
        taskDTO.setAssignedToUserId(task.getAssignedTo().getId());
        taskDTO.setProjectId(task.getProject().getId());

        return taskDTO;
    }
}
