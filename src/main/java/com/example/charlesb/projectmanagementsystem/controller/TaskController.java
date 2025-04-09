package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.RequirementDTO;
import com.example.charlesb.projectmanagementsystem.dto.TaskDTO;
import com.example.charlesb.projectmanagementsystem.entity.Project;
import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.helper.ConversionHelper;
import com.example.charlesb.projectmanagementsystem.service.ProjectService;
import com.example.charlesb.projectmanagementsystem.service.TaskService;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects/{projectId}")
public class TaskController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(ProjectService projectService, TaskService taskService, UserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/task/{taskId}")
    public String viewTaskDetails(@PathVariable Long projectId, @PathVariable Long taskId, Model model) {
        Task task = taskService.findById(taskId);

        model.addAttribute("projectId", projectId);
        model.addAttribute("task", task);
        model.addAttribute("requirements", task.getRequirements());

        return "task_details";
    }

    @GetMapping("/task/new")
    public String newTask(@PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("task", new TaskDTO());

        return "task_form";
    }

    @GetMapping("/task/edit/{taskId}")
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

    @PostMapping("/task/save")
    public String saveTask(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId, @ModelAttribute("task") TaskDTO taskDTO) {
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
            User creator = userService.findUserByEmail(userDetails.getUsername());

            task = new Task();
            task.setCreatedBy(creator);
        }

        task.setProject(savedProject);
        task.setName(taskDTO.name);
        task.setDescription(taskDTO.description);
        task.setPriority(taskDTO.priority);
        task.setStatus(ConversionHelper.intToStatus(taskDTO.status));

        taskService.save(task);

        return "redirect:/list";
    }

    @DeleteMapping("/task/delete/{taskId}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.deleteById(taskId);

        return "redirect:/projects/{projectId}";
    }

    @PostMapping("/task/{taskId}/save_requirement")
    public void saveRequirement(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId, @PathVariable Long taskId, @ModelAttribute("requirement") RequirementDTO requirementDTO) {
        Task task = taskService.findById(taskId);
        Requirement requirement = taskService.findRequirementById(requirementDTO.id);
        User assignedTo = userService.findUserById(requirementDTO.assignedToUserId);

        if (task == null) {
            return;
        } else if (requirement == null) {
            User creator = userService.findUserByEmail(userDetails.getUsername());

            requirement = new Requirement();
            requirement.setCreatedBy(creator);
        }

        requirement.setTask(task);
        requirement.setTitle(requirementDTO.title);
        requirement.setDescription(requirementDTO.description);
        requirement.setStatus(ConversionHelper.intToStatus(requirementDTO.status));

        if (assignedTo != null) {
            requirement.setAssignedTo(assignedTo);
        }

        taskService.saveRequirement(requirement);
    }

    @DeleteMapping("/task/{taskId}/{requirementId}")
    public void deleteRequirement(@PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long requirementId) {
        taskService.deleteRequirementById(requirementId);
    }

}
