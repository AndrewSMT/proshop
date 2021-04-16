package com.andrew.proshop.web.controller;

import com.andrew.proshop.service.user.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping(value = "/ban/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void banUser(@PathVariable Long id) {
        adminService.banUser(id);
    }
}
