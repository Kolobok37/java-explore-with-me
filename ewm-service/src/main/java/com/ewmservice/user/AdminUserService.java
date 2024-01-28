package com.ewmservice.user;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminUserService {
    public ResponseEntity<Object> createUser(User user);

    public ResponseEntity<Object> getUsers(List<Integer> usersId, Integer from, Integer size);

    public ResponseEntity<Object> deleteUser(Integer userId);
}
