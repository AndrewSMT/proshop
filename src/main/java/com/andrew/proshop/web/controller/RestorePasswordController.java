package com.andrew.proshop.web.controller;

import com.andrew.proshop.dto.InfoDto;
import com.andrew.proshop.dto.mail.RestoreMailDto;
import com.andrew.proshop.dto.user.RestorePasswordDto;
import com.andrew.proshop.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restore")
@AllArgsConstructor
public class RestorePasswordController {

    private UserService userService;

    @GetMapping("/mail")
    public ResponseEntity<?> sendRequestOnRestorePassword(@RequestBody RestoreMailDto restoreDto) {
        userService.sendRestoreRequest(restoreDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public InfoDto restorePassword(@RequestBody RestorePasswordDto restorePasswordDto) {
        return userService.restorePassword(restorePasswordDto);
    }

}
