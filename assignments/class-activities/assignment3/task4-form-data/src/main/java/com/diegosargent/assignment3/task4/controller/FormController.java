package com.diegosargent.assignment3.task4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    @GetMapping("/showForm")
    public String showForm() {
        return "form";
    }

    @GetMapping("/processForm")
    public String processForm() {
        return "result";
    }

}
