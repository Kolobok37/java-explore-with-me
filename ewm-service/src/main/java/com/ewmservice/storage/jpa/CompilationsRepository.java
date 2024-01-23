package com.ewmservice.storage.jpa;

import com.ewmservice.model.Compilation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CompilationsRepository extends JpaRepository<Compilation, Integer> {
    List<Compilation> findAllByPinned(Boolean pinned, Pageable paging);
}


