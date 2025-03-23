package com.example.charlesb.projectmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping(path = {"/", "/home"})
    public String homepage() {
        return "home";
    }

}
