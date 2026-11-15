package com.diegosargent.assignment3.task5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieController {

    @GetMapping("/showForm")
    public String showForm() {
        return "movie-form";
    }

    @GetMapping("/movie")
    public String movieName(@RequestParam(defaultValue = "") String movieName, Model model) {
        String movie = movieName.trim();

        model.addAttribute("message", "Welcome to the Software Engineering Class");
        model.addAttribute("movieName", movie);
        model.addAttribute("favorite", movie + " is my favorite movie");

        return "movie-result";
    }

}
