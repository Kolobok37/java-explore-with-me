package com.ewmservice.storage;

import com.ewmservice.exception.NotFoundException;
import com.ewmservice.model.Compilation;
import com.ewmservice.storage.jpa.CompilationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompilationsStorage {
    @Autowired
    CompilationsRepository compilationsRepository;

    public Compilation createCompilation(Compilation compilation) {
        return compilationsRepository.save(compilation);
    }

    public void deleteCompilation(Integer compId) {
        compilationsRepository.deleteById(compId);
    }

    public Compilation getCompilation(Integer compId) {
        return compilationsRepository.findById(compId)
                .orElseThrow(()-> new NotFoundException("Compilation is not found"));
    }

    public Compilation updateCompilation(Compilation compilation) {
        return compilationsRepository.save(compilation);
    }

    public List<Compilation> getCompilations(Pageable paging) {
        return compilationsRepository.findAll(paging).toList();
    }
    public List<Compilation> getCompilations(Boolean pinned, Pageable paging) {
        return compilationsRepository.findAllByPinned(pinned,paging);
    }
}
