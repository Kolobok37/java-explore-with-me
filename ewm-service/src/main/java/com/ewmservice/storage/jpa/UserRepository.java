package com.ewmservice.storage.jpa;

import com.ewmservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findByIdIn(List<Integer> ids, Pageable pageable);
}
