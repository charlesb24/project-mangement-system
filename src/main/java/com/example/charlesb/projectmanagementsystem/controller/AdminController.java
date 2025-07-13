package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dao.RoleRepository;
import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.Role;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.enums.LinkType;
import com.example.charlesb.projectmanagementsystem.helper.HistoryHelper;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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

        users.sort(new Comparator<UserDTO>() {
            @Override
            public int compare(UserDTO o1, UserDTO o2) {
                return (int) (o1.getId() - o2.getId());
            }
        });

        model.addAttribute("users", users);
        model.addAttribute("links", HistoryHelper.getHistoryForAdminUserList());
        model.addAttribute("adminView", true);

        return "user_list";
    }

    @PostMapping("/users/toggle")
    public String toggleUserEnabled(@RequestParam Long userId) {
        User user = userService.findUserById(userId);

        user.setEnabled(!user.isEnabled());

        userService.updateUser(user);
        userService.reloadUserByEmail(user.getEmail());

        return "redirect:/admin/users/list";
    }

    @GetMapping("/users/{userId}/edit")
    public String editUser(@PathVariable Long userId, Model model) {
        User foundUser = userService.findUserById(userId);

        model.addAttribute("user", userService.mapToDTO(foundUser));
        model.addAttribute("managers", userService.findManagers());
        model.addAttribute("links", HistoryHelper.getHistoryForAdminUser(userId, LinkType.EDIT));
        model.addAttribute("selfEdit", false);

        return "user_form";
    }

    @PostMapping("/users/promote")
    public String promoteUser(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long userId, @RequestParam String role) {
        User promotingUser = userService.findUserByEmail(userDetails.getUsername());
        Role roleToAdd = roleRepository.findByName("ROLE_" + role);

        if (role.equalsIgnoreCase("owner") && promotingUser.hasRole("ROLE_OWNER")) {
            promotingUser.removeRole(roleToAdd);

            userService.updateUser(promotingUser);
            userService.reloadUserByEmail(promotingUser.getEmail());
        } else if (!promotingUser.hasRole("ROLE_OWNER") && (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("owner"))) {
            return "redirect:/admin/users/list?error=auth";
        }

        User userToBePromoted = userService.findUserById(userId);

        userToBePromoted.addRole(roleToAdd);

        userService.updateUser(userToBePromoted);
        userService.reloadUserByEmail(userToBePromoted.getEmail());

        return "redirect:/admin/users/list";
    }

    @PostMapping("/users/demote")
    public String demoteUser(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long userId, @RequestParam String role) {
        User demotingUser = userService.findUserByEmail(userDetails.getUsername());
        User userToBeDemoted = userService.findUserById(userId);

        if (!demotingUser.hasRole("ROLE_OWNER") && !role.equalsIgnoreCase("manager")) {
            return "redirect:/admin/users/list?error=auth";
        }

        Role roleToRemove = roleRepository.findByName("ROLE_" + role);

        userToBeDemoted.removeRole(roleToRemove);

        userService.updateUser(userToBeDemoted);
        userService.reloadUserByEmail(userToBeDemoted.getEmail());

        return "redirect:/admin/users/list";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user") UserDTO userDTO) {
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

        if (userDTO.getManagerId() != null) {
            if (userService.findUserById(userDTO.getManagerId()) != null) {
                user.setManagerId(userDTO.getManagerId());
            }
        }

        if (userDTO.getContactMethod() != null) {
            user.setContactMethod(userDTO.getContactMethod());
        }

        userService.updateUser(user);

        return "redirect:/admin/users/list";
    }

    @DeleteMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return "redirect:/admin/users/list";
    }

}
