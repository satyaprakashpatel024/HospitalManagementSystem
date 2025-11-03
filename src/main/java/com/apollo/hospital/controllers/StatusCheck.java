package com.apollo.hospital.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StatusCheck {
    @GetMapping("/status")
    public String status() {
        return "Apollo Hospital Management System is running.....😇";
    }
}
