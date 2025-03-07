package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.TaskDTO;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list")
    public String viewTasks(Model model) {
        List<Task> tasks = taskService.findAll();

        model.addAttribute("tasks", tasks);

        return "task_view";
    }

    @GetMapping("/details/{id}")
    public String viewTaskDetails(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        List<Task> childTasks = taskService.findChildTasks(task);

        model.addAttribute("task", task);
        model.addAttribute("childTasks", childTasks);

        return "task_details";
    }

    @GetMapping("/new")
    public String newTask(Model model) {
        model.addAttribute("task", new TaskDTO());

        return "task_form";
    }

    @PostMapping("/new")
    public String saveTask(@ModelAttribute("task") TaskDTO taskDTO) {
        Task task;

        Task savedTask = taskService.findById(taskDTO.id);

        if (savedTask != null) {
            task = savedTask;
        } else {
            task = new Task();

            task.setParentTaskId(taskDTO.parentId);
            task.setLevel(taskDTO.level);
        }

        task.setName(taskDTO.name);
        task.setDescription(taskDTO.description);
        task.setPriority(taskDTO.priority);
        task.setStatus(taskDTO.status);

        taskService.save(task);

        return "redirect:/list";
    }

}
