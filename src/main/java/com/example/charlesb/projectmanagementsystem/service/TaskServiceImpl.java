package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dao.TaskRepository;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public List<Task> findChildTasks(Long taskId) {
        return taskRepository.findAllByParentTaskId(taskId);
    }

    @Override
    public List<Task> findChildTasks(Task parentTask) {
        return findChildTasks(parentTask.getParentTaskId());
    }

    @Override
    public Task findParentTask(Long childTaskId) {
        Optional<Task> childTask = taskRepository.findById(childTaskId);

        return childTask.flatMap(task -> taskRepository.findById(task.getParentTaskId())).orElse(null);
    }

    @Override
    public Task findParentTask(Task childTask) {
        return findParentTask(childTask.getTaskId());
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteById(Long taskId) {
        taskRepository.findAllByParentTaskId(taskId).forEach(task -> deleteById(task.getTaskId()));
        taskRepository.deleteById(taskId);
    }
}
