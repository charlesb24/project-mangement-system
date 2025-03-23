package com.example.charlesb.projectmanagementsystem.controller;

import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDTO userDTO = new UserDTO();

        model.addAttribute("user", userDTO);

        return "registration_form";
    }

    @PostMapping("/register/save")
    public String processRegistrationForm(
            @Valid @ModelAttribute("user") UserDTO userDTO,
            BindingResult result,
            Model model
    ) {
        User userExists = userService.findUserByEmail(userDTO.getEmail());

        if (userExists != null && userExists.getEmail() != null && !userExists.getEmail().isEmpty()) {
            result.rejectValue("email", "email.exists", "An account with that email already exists");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "registration_form";
        }

        userService.saveUser(userDTO);

        return "redirect:/register?success";
    }

}
