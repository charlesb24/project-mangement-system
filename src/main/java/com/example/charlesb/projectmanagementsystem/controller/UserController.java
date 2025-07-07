package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.entity.Project;
import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.service.ProjectService;
import com.example.charlesb.projectmanagementsystem.service.TaskService;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserController(UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping("/user/edit")
    public String editSelf(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findUserByEmail(userDetails.getUsername());

        model.addAttribute("user", user);

        return "user_form";
    }

    @GetMapping("/user")
    public String showSelf(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findUserByEmail(userDetails.getUsername());

        List<Project> assignedProjects = projectService.findAllAssignedToUser(user);
        List<Project> createdProjects = projectService.findAllCreatedByUser(user);

        List<Task> assignedTasks = taskService.findAllAssignedToUser(user);
        List<Task> createdTasks = taskService.findAllCreatedByUser(user);

        List<Requirement> assignedRequirements = taskService.findAllRequirementsAssignedToUser(user);
        List<Requirement> createdRequirements = taskService.findAllRequirementsCreatedByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("assignedProjects", assignedProjects);
        model.addAttribute("createdProjects", createdProjects);
        model.addAttribute("assignedTasks", assignedTasks);
        model.addAttribute("createdTasks", createdTasks);
        model.addAttribute("assignedRequirements", assignedRequirements);
        model.addAttribute("createdRequirements", createdRequirements);

        return "user_details";
    }

}
