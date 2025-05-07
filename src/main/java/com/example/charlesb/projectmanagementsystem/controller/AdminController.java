package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/users/toggle")
    public void toggleUserEnabled(@RequestParam Long userId) {
        User user = userService.findUserById(userId);

        user.setEnabled(!user.isEnabled());

        userService.updateUser(user);
    }

    @GetMapping("/users/{userId}/edit")
    public String editUser(@PathVariable Long userId, Model model) {
        User foundUser = userService.findUserById(userId);
        User manager = userService.findUserById(foundUser.getManagerId());

        model.addAttribute("user", userService.mapToDTO(foundUser));
        model.addAttribute("managers", userService.findManagers());
        model.addAttribute("assignedUser", userService.mapToDTO(manager));

        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(@RequestParam UserDTO userDTO) {
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

        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setLocked(userDTO.isLocked());

        return "redirect:/users/list";
    }

}
