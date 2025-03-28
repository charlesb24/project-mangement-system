package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/list")
    public String listUsers(Model model) {
        List<UserDTO> users = userService.findAllUsers();

        model.addAttribute("users", users);

        return "user_list";
    }

    @PostMapping("/users/")
    public void toggleUserEnabled(@RequestParam Long userId) {
        User user = userService.findUserById(userId);

        user.setEnabled(!user.isEnabled());

        userService.updateUser(user);
    }

    @GetMapping("/users/edit")
    public String editUser(Model model) {
        // TODO: allow editing of existing users
        model.addAttribute("user", new UserDTO());

        return "user_form";
    }

}
