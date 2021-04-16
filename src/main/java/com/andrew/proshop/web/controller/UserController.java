package com.andrew.proshop.web.controller;

import com.andrew.proshop.dto.user.CreateUpdateUserDto;
import com.andrew.proshop.dto.user.UserDto;
import com.andrew.proshop.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getUsersList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody CreateUpdateUserDto newUser) {
        UserDto user = userService.createUser(newUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public void updateUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody CreateUpdateUserDto updateUser) {
        return ResponseEntity.ok(userService.updateUser(id, updateUser));
    }

}
