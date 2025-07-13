package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.Project;
import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.helper.HistoryHelper;
import com.example.charlesb.projectmanagementsystem.service.ProjectService;
import com.example.charlesb.projectmanagementsystem.service.TaskService;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/user")
    public String showSelf(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findUserByEmail(userDetails.getUsername());

        List<Project> assignedProjects = projectService.findAllAssignedToUser(user);
        List<Project> createdProjects = projectService.findAllCreatedByUser(user);

        List<Task> assignedTasks = taskService.findAllAssignedToUser(user);
        List<Task> createdTasks = taskService.findAllCreatedByUser(user);

        List<Requirement> assignedRequirements = taskService.findAllRequirementsAssignedToUser(user);
        List<Requirement> createdRequirements = taskService.findAllRequirementsCreatedByUser(user);

        if (user.getManagerId() != null) {
            model.addAttribute("manager", userService.findUserById(user.getManagerId()));
        }

        model.addAttribute("user", user);
        model.addAttribute("assignedProjects", assignedProjects);
        model.addAttribute("createdProjects", createdProjects);
        model.addAttribute("assignedTasks", assignedTasks);
        model.addAttribute("createdTasks", createdTasks);
        model.addAttribute("assignedRequirements", assignedRequirements);
        model.addAttribute("createdRequirements", createdRequirements);
        model.addAttribute("userView", true);

        return "user_details";
    }

    @GetMapping("/user/edit")
    public String editSelf(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findUserByEmail(userDetails.getUsername());

        model.addAttribute("user", userService.mapToDTO(user));
        model.addAttribute("links", HistoryHelper.getHistoryForUserSelfEdit());
        model.addAttribute("selfEdit", true);

        return "user_form";
    }

    @PostMapping("/user/save")
    public String saveSelf(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("user") UserDTO userDTO) {
        User user;
        User foundUser = userService.findUserById(userDTO.getId());

        if (foundUser == null) {
            user = new User();
        } else {
            user = foundUser;
        }

        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());

        user.setPhone(userDTO.getPhone());

        if (userDTO.getContactMethod() != null) {
            user.setContactMethod(userDTO.getContactMethod());
        }

        userService.updateUser(user);

        return "redirect:/user";
    }

}
