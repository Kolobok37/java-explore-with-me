package com.ewmservice.user;

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
public class AdminUserController {
    @Autowired
    AdminUserServiceImpl adminUserService;

    @PostMapping("/admin/users")
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
        return adminUserService.createUser(user);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Object> getUsers(@RequestParam(required = false) List<Integer> ids,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        return adminUserService.getUsers(ids, from, size);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer userId) {
        return adminUserService.deleteUser(userId);
    }
}
