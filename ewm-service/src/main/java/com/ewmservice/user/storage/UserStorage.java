package com.ewmservice.user.storage;

import com.ewmservice.exception.DuplicateDataException;
import com.ewmservice.exception.NotFoundException;
import com.ewmservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserStorage {
    @Autowired
    UserRepository userRepository;

    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateDataException("Email is not unique.");
        }
    }

    public User getUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));
    }

    public List<User> getUsers(List<Integer> usersId, Pageable pageable) {
        if (usersId == null || usersId.isEmpty()) {
            return userRepository.findAll(pageable).toList();
        } else {
            return userRepository.findByIdIn(usersId, pageable).toList();
        }
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
