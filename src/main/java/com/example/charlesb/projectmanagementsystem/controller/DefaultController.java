package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.service.ProjectService;
import com.example.charlesb.projectmanagementsystem.service.TaskService;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public DefaultController(ProjectService projectService, TaskService taskService, UserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping(path = {"/", "/home"})
    public String homepage() {
        return "home";
    }

    @GetMapping(path = "/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());

        model.addAttribute("inProgressProjects", projectService.findAllInProgressByUser(currentUser.getId()));
        model.addAttribute("inProgressTasks", taskService.findAllInProgressByUser(currentUser.getId()));

        return "dashboard";
    }

}
