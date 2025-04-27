package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.RequirementDTO;
import com.example.charlesb.projectmanagementsystem.dto.TaskDTO;
import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
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
@RequestMapping("/projects/{projectId}/task")
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

    @GetMapping("/{taskId}")
    public String viewTaskDetails(@PathVariable Long projectId, @PathVariable Long taskId, Model model) {
        Task task = taskService.findById(taskId);

        model.addAttribute("projectId", projectId);
        model.addAttribute("task", task);
        model.addAttribute("requirements", task.getRequirements());

        return "task_details";
    }

    @GetMapping("/new")
    public String newTask(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("assignableUsers", userService.findAssignableUsers(userDetails));
        model.addAttribute("assignedUser", null);

        return "task_form";
    }

    @GetMapping("/edit/{taskId}")
    public String editTask(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId, @PathVariable Long taskId, Model model) {
        Task task = taskService.findById(taskId);

        if (task == null) {
            return "redirect:/projects/{projectId}/task/new";
        }

        TaskDTO taskDTO = taskService.mapToDTO(task);

        model.addAttribute("projectId", projectId);
        model.addAttribute("task", taskDTO);
        model.addAttribute("assignableUsers", userService.findAssignableUsers(userDetails));
        model.addAttribute("assignedUser", userService.mapToDTO(task.getAssignedTo()));

        return "task_form";
    }

    @PostMapping("/save")
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
            task.setProject(savedProject);
        }

        task.setName(taskDTO.name);
        task.setDescription(taskDTO.description);
        task.setPriority(taskDTO.priority);
        task.setStatus(ConversionHelper.intToStatus(taskDTO.status));

        taskService.save(task);

        return "redirect:/list";
    }

    @DeleteMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.deleteById(taskId);

        return "redirect:/projects/{projectId}";
    }

    @GetMapping("/{taskId}/requirement/new")
    public String newRequirement(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId, @PathVariable Long taskId, Model model) {
        model.addAttribute("requirement", new RequirementDTO());
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("assignableUsers", userService.findAssignableUsers(userDetails));
        model.addAttribute("assignedUser", null);

        return "requirement_form";
    }

    @GetMapping("/{taskId}/requirement/{requirementId}/edit")
    public String editRequirement(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long requirementId, Model model) {
        Requirement requirement = taskService.findRequirementById(requirementId);

        if (requirement == null) {
            return "redirect:/projects/{projectId}/task/{taskId}/requirement/new";
        }

        RequirementDTO requirementDTO = mapToDTO(requirement);

        model.addAttribute("requirement", requirementDTO);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("assignableUsers", userService.findAssignableUsers(userDetails));
        model.addAttribute("assignedUser", userService.mapToDTO(requirement.getAssignedTo()));

        return "requirement_form";
    }

    @PostMapping("/{taskId}/requirement/save")
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
            requirement.setTask(task);
        }

        requirement.setTitle(requirementDTO.title);
        requirement.setDescription(requirementDTO.description);
        requirement.setStatus(ConversionHelper.intToStatus(requirementDTO.status));

        if (assignedTo != null) {
            requirement.setAssignedTo(assignedTo);
        }

        taskService.saveRequirement(requirement);
    }

    @DeleteMapping("/{taskId}/requirement/{requirementId}")
    public void deleteRequirement(@PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long requirementId) {
        taskService.deleteRequirementById(requirementId);
    }

    private RequirementDTO mapToDTO(Requirement requirement) {
        RequirementDTO requirementDTO = new RequirementDTO();

        requirementDTO.setId(requirement.getId());
        requirementDTO.setTitle(requirement.getTitle());
        requirementDTO.setDescription(requirement.getDescription());
        requirementDTO.setStatus(ConversionHelper.statusToInt(requirement.getStatus()));
        requirementDTO.setAssignedToUserId(requirement.getAssignedTo().getId());

        return requirementDTO;
    }

}
