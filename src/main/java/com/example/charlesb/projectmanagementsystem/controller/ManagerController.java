package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.Project;
import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.exception.ManagerException;
import com.example.charlesb.projectmanagementsystem.helper.HistoryHelper;
import com.example.charlesb.projectmanagementsystem.service.ProjectService;
import com.example.charlesb.projectmanagementsystem.service.TaskService;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;

@Controller
public class ManagerController {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    public ManagerController(UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping("/manager/users/list")
    public String showSubordinates(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<UserDTO> users = userService.findAssignableUsers(userDetails);

        model.addAttribute("users", users);
        model.addAttribute("links", HistoryHelper.getHistoryForManagerList());

        return "user_list";
    }

    @GetMapping("/manager/user/{userId}")
    public String showUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long userId, Model model) throws ManagerException {
        User manager = userService.findUserByEmail(userDetails.getUsername());
        User user = userService.findUserById(userId);

        if (!Objects.equals(user.getManagerId(), manager.getId())) {
            throw new ManagerException("You are not the manager of this user, or the user does not exist.");
        }

        List<Project> assignedProjects = projectService.findAllAssignedToUser(user);
        List<Project> createdProjects = projectService.findAllCreatedByUser(user);

        List<Task> assignedTasks = taskService.findAllAssignedToUser(user);
        List<Task> createdTasks = taskService.findAllCreatedByUser(user);

        List<Requirement> assignedRequirements = taskService.findAllRequirementsAssignedToUser(user);
        List<Requirement> createdRequirements = taskService.findAllRequirementsCreatedByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("manager", manager);
        model.addAttribute("assignedProjects", assignedProjects);
        model.addAttribute("createdProjects", createdProjects);
        model.addAttribute("assignedTasks", assignedTasks);
        model.addAttribute("createdTasks", createdTasks);
        model.addAttribute("assignedRequirements", assignedRequirements);
        model.addAttribute("createdRequirements", createdRequirements);

        model.addAttribute("links",  HistoryHelper.getHistoryForManagedUser(userId));

        return "user_details";
    }

}
