package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.TaskDTO;
import com.example.charlesb.projectmanagementsystem.entity.Project;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.helper.ConversionHelper;
import com.example.charlesb.projectmanagementsystem.service.ProjectService;
import com.example.charlesb.projectmanagementsystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProjectController {

    private final TaskService taskService;
    private final ProjectService projectService;

    @Autowired
    public ProjectController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public String viewProjects(Model model) {
        List<Project> projects = projectService.findAll();

        model.addAttribute("projects", projects);

        return "project_list";
    }

    @GetMapping("/projects/{projectId}")
    public String viewProjectDetails(@PathVariable Long projectId, Model model) {
        Project project = projectService.findById(projectId);

        model.addAttribute("project", project);

        return "project_details";
    }

    @GetMapping("/projects/new")
    public String newProject(Model model) {
        model.addAttribute("project", new Project());

        return "project_form";
    }

    @GetMapping("/projects/{projectId}/edit")
    public String editProject(@PathVariable Long projectId, Model model) {
        // TODO: check for existence of project with id, if non-existent, send user back to project list with an error message

        model.addAttribute("project", projectService.findById(projectId));

        return "project_form";
    }

    @PostMapping("/projects/save")
    public String saveProject(@ModelAttribute("project") Project project) {
        // TODO: add save functionality for projects

        return "redirect:/projects";
    }

    @DeleteMapping("/projects/{projectId}/delete/")
    public String deleteProject(@PathVariable Long projectId) {
        projectService.deleteById(projectId);

        return "redirect:/projects";
    }

    @GetMapping("/projects/{projectId}/task/{taskId}")
    public String viewTaskDetails(@PathVariable Long projectId, @PathVariable Long taskId, Model model) {
        Task task = taskService.findById(taskId);

        model.addAttribute("projectId", projectId);
        model.addAttribute("task", task);

        return "task_details";
    }

    @GetMapping("/projects/{projectId}/task/new")
    public String newTask(@PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("task", new TaskDTO());

        return "task_form";
    }

    @GetMapping("/projects/{projectId}/task/edit/{taskId}")
    public String editTask(@PathVariable Long projectId, @PathVariable Long taskId, Model model) {
        Task task = taskService.findById(taskId);
        TaskDTO taskDTO = new TaskDTO();

        if (task != null) {
            taskDTO.setId(task.getTaskId());
            taskDTO.setName(task.getName());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setStatus(ConversionHelper.statusToInt(task.getStatus()));
            taskDTO.setPriority(task.getPriority());
        }

        model.addAttribute("projectId", projectId);
        model.addAttribute("task", taskDTO);

        return "task_form";
    }

    @PostMapping("/projects/{projectId}/task/save")
    public String saveTask(@PathVariable Long projectId, @ModelAttribute("task") TaskDTO taskDTO, @AuthenticationPrincipal User user) {
        Task task;

        Task savedTask = taskService.findById(taskDTO.id);
        Project savedProject = projectService.findById(projectId);

        if (savedProject == null) {
            // TODO: add error message stating the project couldn't be found
            return "redirect:/projects";
        }

        if (savedTask != null) {
            task = savedTask;
        } else {
            task = new Task();
        }

        task.setProject(savedProject);
        task.setName(taskDTO.name);
        task.setDescription(taskDTO.description);
        task.setPriority(taskDTO.priority);
        task.setStatus(ConversionHelper.intToStatus(taskDTO.status));
        task.setCreatedBy(user);

        taskService.save(task);

        return "redirect:/list";
    }

    @DeleteMapping("/projects/{projectId}/task/delete/{taskId}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.deleteById(taskId);

        return "redirect:/projects/{projectId}";
    }

}
