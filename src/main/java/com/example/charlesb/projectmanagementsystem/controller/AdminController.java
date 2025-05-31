package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dao.RoleRepository;
import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.Role;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
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

    // TODO: ensure ONLY the user with the OWNER role can promote users to ADMIN or OWNER,
    // TODO: and that there is only ever one OWNER by demoting the current OWNER at the same time

    @PostMapping("/users/promote")
    public String promoteUser(@RequestParam Long userId, @RequestParam String role) {
        User userToBePromoted = userService.findUserById(userId);

        Role roleToAdd = roleRepository.findByName("ROLE_" + role);

        userToBePromoted.addRole(roleToAdd);

        return "redirect:/admin/users/list";
    }

    @PostMapping("/users/demote")
    public String demoteUser(@RequestParam Long userId, @RequestParam String role) {
        User userToBeDemoted = userService.findUserById(userId);

        Role roleToRemove = roleRepository.findByName("ROLE_" + role);

        userToBeDemoted.removeRole(roleToRemove);

        return "redirect:/admin/users/list";
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
