package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.ProjectDTO;
import com.example.charlesb.projectmanagementsystem.dto.TaskDTO;
import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.Project;
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

import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
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
        model.addAttribute("links", HistoryHelper.getHistoryForProject(projectId, LinkType.VIEW));

        return "project_details";
    }

    @GetMapping("/projects/new")
    public String newProject(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("assignableUsers", userService.findAssignableUsers(userDetails));
        model.addAttribute("links", HistoryHelper.getHistoryForProject(0L, LinkType.NEW));

        return "project_form";
    }

    @GetMapping("/projects/{projectId}/edit")
    public String editProject(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId, Model model) {
        Project foundProject = projectService.findById(projectId);

        if (foundProject == null) {
            return "redirect:/projects/new";
        }

        model.addAttribute("project", projectService.mapToDTO(foundProject));
        model.addAttribute("assignableUsers", userService.findAssignableUsers(userDetails));
        model.addAttribute("links", HistoryHelper.getHistoryForProject(projectId, LinkType.EDIT));

        return "project_form";
    }

    @PostMapping("/projects/save")
    public String saveProject(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("project") ProjectDTO projectDTO) {
        Project project = projectService.mapToProject(projectDTO);

        if (project.getCreatedBy() == null) {
            User creator = userService.findUserByEmail(userDetails.getUsername());
            project.setCreatedBy(creator);
        }

        projectService.save(project);

        return "redirect:/projects/" + project.getId();
    }

    @DeleteMapping("/projects/{projectId}/delete/")
    public String deleteProject(@PathVariable Long projectId) {
        projectService.deleteById(projectId);

        return "redirect:/projects";
    }

}
