package com.diegosargent.assignment3.task1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping(value = "/", produces = "text/html")
    public String profile() {
        return "<h1>Diego</h1><p>Favorite movie: Wall-E</p><p>Favorite music: Rock</p>";
    }

    @GetMapping("/name")
    public String name() {
        return "Diego";
    }

    @GetMapping("/movie")
    public String movie() {
        return "Wall-E";
    }

    @GetMapping("/music")
    public String music() {
        return "Rock";
    }

}
