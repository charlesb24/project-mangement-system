package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.RequirementDTO;
import com.example.charlesb.projectmanagementsystem.dto.TaskDTO;
import com.example.charlesb.projectmanagementsystem.entity.Project;
import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.enums.LinkType;
import com.example.charlesb.projectmanagementsystem.helper.ConversionHelper;
import com.example.charlesb.projectmanagementsystem.helper.HistoryHelper;
import com.example.charlesb.projectmanagementsystem.service.ProjectService;
import com.example.charlesb.projectmanagementsystem.service.TaskService;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("links", HistoryHelper.getHistoryForTask(taskId, projectId, LinkType.VIEW));

        return "task_details";
    }

    @GetMapping("/new")
    public String newTask(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("assignableUsers", userService.findAssignableUsers(userDetails));
        model.addAttribute("assignedUser", null);
        model.addAttribute("links", HistoryHelper.getHistoryForTask(0L, projectId, LinkType.NEW));

        return "task_form";
    }

    @GetMapping("/{taskId}/edit")
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
        model.addAttribute("links", HistoryHelper.getHistoryForTask(taskId, projectId, LinkType.EDIT));

        return "task_form";
    }

    @PostMapping("/save")
    public String saveTask(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId, @ModelAttribute("task") TaskDTO taskDTO) {
        Project savedProject = projectService.findById(projectId);

        if (savedProject == null) {
            return "redirect:/projects?no_project_id";
        }

        Task task = taskService.mapToTask(taskDTO);

        if (task.getCreatedBy() == null) {
            User creator = userService.findUserByEmail(userDetails.getUsername());
            task.setCreatedBy(creator);
            task.setProject(savedProject);
        }

        taskService.save(task);

        return "redirect:/projects/" + projectId + "/task/" + task.getTaskId();
    }

    @PostMapping("/delete")
    public String deleteTask(@PathVariable Long projectId, @RequestParam Long taskId) {
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
        model.addAttribute("links", HistoryHelper.getHistoryForRequirement(taskId, projectId, LinkType.NEW));

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
        model.addAttribute("links", HistoryHelper.getHistoryForRequirement(taskId, projectId, LinkType.EDIT));

        return "requirement_form";
    }

    @PostMapping("/{taskId}/requirement/save")
    public String saveRequirement(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId, @PathVariable Long taskId, @ModelAttribute("requirement") RequirementDTO requirementDTO) {
        Task task = taskService.findById(taskId);
        Requirement requirement = taskService.findRequirementById(requirementDTO.id);
        User assignedTo = userService.findUserById(requirementDTO.assignedToUserId);

        if (task == null) {
            return "redirect:/projects/{projectId}/task/{taskId}/requirement/new";
        } else if (requirement == null) {
            User creator = userService.findUserByEmail(userDetails.getUsername());

            requirement = new Requirement();
            requirement.setCreatedBy(creator);
            requirement.setTask(task);
        }

        requirement.setTitle(requirementDTO.title);
        requirement.setDescription(requirementDTO.description);
        requirement.setStatus(ConversionHelper.intToStatus(requirementDTO.status));

        requirement.setAssignedTo(assignedTo);

        taskService.saveRequirement(requirement);

        return "redirect:/projects/" + projectId + "/task/" + taskId;
    }

    @PostMapping("/{taskId}/requirement/delete")
    public String deleteRequirement(@PathVariable Long projectId, @PathVariable Long taskId, @RequestParam Long requirementId) {
        taskService.deleteRequirementById(requirementId);

        return "redirect:/projects/" + projectId + "/task/" + taskId;
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
