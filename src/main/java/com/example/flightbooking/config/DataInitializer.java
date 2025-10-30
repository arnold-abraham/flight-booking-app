package com.example.flightbooking.config;

import com.example.flightbooking.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserService userService) {
        return args -> userService.ensureAdminExists();
    }
}
