package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dao.RequirementRepository;
import com.example.charlesb.projectmanagementsystem.dao.TaskRepository;
import com.example.charlesb.projectmanagementsystem.dao.UserRepository;
import com.example.charlesb.projectmanagementsystem.dto.TaskDTO;
import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.enums.Status;
import com.example.charlesb.projectmanagementsystem.helper.ConversionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    public List<Task> findAllAssignedToUser(User user) {
        return taskRepository.findAllByAssignedTo(user);
    }

    @Override
    public List<Task> findAllCreatedByUser(User user) {
        return taskRepository.findAllByCreatedBy(user);
    }

    @Override
    public List<Task> findAllInProgressByUser(User user) {
        List<Task> tasks = taskRepository.findAllByAssignedToAndStatus(user, Status.IN_PROGRESS);

        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return ConversionHelper.priorityToInt(o1.getPriority()) - ConversionHelper.priorityToInt(o2.getPriority());
            }
        });

        return tasks;
    }

    @Override
    public List<Requirement> findAllRequirementsCreatedByUser(User user) {
        return requirementRepository.findAllByAssignedTo(user);
    }

    @Override
    public List<Requirement> findAllRequirementsAssignedToUser(User user) {
        return requirementRepository.findAllByCreatedBy(user);
    }

    @Override
    public List<Requirement> findAllRequirementsInProgressByUser(User user) {
        return requirementRepository.findAllByAssignedToAndStatus(user, Status.IN_PROGRESS);
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
        System.out.println("start killing " + taskId);

        taskRepository.deleteById(taskId);

        System.out.println("finish killing " + taskId);
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

        if (taskDTO.getAssignedToUserId() == 0) {
            task.setAssignedTo(null);
        }

        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(ConversionHelper.intToPriority(taskDTO.getPriority()));
        task.setStatus(ConversionHelper.intToStatus(taskDTO.getStatus()));

        return task;
    }

    @Override
    public TaskDTO mapToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getTaskId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setPriority(ConversionHelper.priorityToInt(task.getPriority()));
        taskDTO.setStatus(ConversionHelper.statusToInt(task.getStatus()));
        taskDTO.setProjectId(task.getProject().getId());

        if (task.getAssignedTo() != null) {
            taskDTO.setAssignedToUserId(task.getAssignedTo().getId());
        }

        return taskDTO;
    }

}
