package com.ewmservice.user;

import com.ewmservice.Paging;
import com.ewmservice.user.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    UserStorage userStorage;

    public ResponseEntity<Object> createUser(User user) {
        return new ResponseEntity<>(userStorage.createUser(user), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getUsers(List<Integer> usersId, Integer from, Integer size) {
        return new ResponseEntity<>(userStorage.getUsers(usersId, Paging.paging(from, size)), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteUser(Integer userId) {
        userStorage.deleteUser(userStorage.getUser(userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
