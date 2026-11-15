package com.diegosargent.assignment3.task3;

import org.springframework.stereotype.Component;

@Component
public class BasketballCoach implements Coach {

    @Override
    public String getDailyWorkout() {
        return "Tennis is my favorite basketball.";
    }

}
