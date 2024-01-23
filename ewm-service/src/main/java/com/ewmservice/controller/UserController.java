package com.ewmservice.controller;

import com.ewmservice.model.User;
import com.ewmservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/admin/users")
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Object> getUsers(@RequestParam(required = false) List<Integer> ids,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        return userService.getUsers(ids, from, size);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }
}
