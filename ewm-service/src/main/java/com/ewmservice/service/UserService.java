package com.ewmservice.service;

import com.ewmservice.Paging;
import com.ewmservice.model.User;
import com.ewmservice.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserStorage userStorage;

    public ResponseEntity<Object> createUser(User user) {
        return new ResponseEntity<>(userStorage.createUser(user), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getUsers(List<Integer> usersId, Integer from, Optional<Integer> size) {
        return new ResponseEntity<>(userStorage.getUsers(usersId, Paging.paging(from, size)), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteUser(Integer userId) {
        userStorage.deleteUser(userStorage.getUser(userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
