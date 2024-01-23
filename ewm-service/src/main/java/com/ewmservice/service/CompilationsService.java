package com.ewmservice.service;

import com.ewmservice.Paging;
import com.ewmservice.dto.CompilationDto;
import com.ewmservice.dto.CompilationInDto;
import com.ewmservice.dto.CompilationUpdateDto;
import com.ewmservice.dto.mappers.MapperCompilation;
import com.ewmservice.exception.DeleteCompilationException;
import com.ewmservice.exception.UpdateEntityException;
import com.ewmservice.model.Compilation;
import com.ewmservice.model.Event;
import com.ewmservice.storage.CompilationsStorage;
import com.ewmservice.storage.EventStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompilationsService {
    @Autowired
    CompilationsStorage compilationsStorage;
    @Autowired
    EventStorage eventStorage;

    public ResponseEntity<Object> createCompilation(CompilationInDto compilationInDto) {
        List<Event> events = new ArrayList<>();
        if (compilationInDto.getEvents() != null) {
            events = eventStorage.getEventsById(compilationInDto.getEvents());
        }
        Compilation compilation = MapperCompilation.mapToCompilation(compilationInDto);
        compilation.setEvents(events);
        if (compilationInDto.getPinned() == null) {
            compilation.setPinned(false);
        }
        CompilationDto compilationDto = MapperCompilation
                .mapToCompilationDto(compilationsStorage.createCompilation(compilation));
        return new ResponseEntity<>(compilationDto, HttpStatus.CREATED);

    }

    public ResponseEntity<Object> deleteCompilation(Integer compId) {
        Compilation compilation = compilationsStorage.getCompilation(compId);
        if (!compilation.getEvents().isEmpty()) {
            throw new DeleteCompilationException("There are related events");
        }
        compilationsStorage.deleteCompilation(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> updateCompilation(CompilationUpdateDto compilationInDto, Integer compId) {
        Compilation compilation = compilationsStorage.getCompilation(compId);
        if (compilationInDto.getEvents() != null) {
            compilation.setEvents(eventStorage.getEventsById(compilationInDto.getEvents()));
        }
        if (compilationInDto.getTitle() != null) {
            if (compilationInDto.getTitle().isBlank() || compilationInDto.getTitle().length() > 50
                    || compilationInDto.getTitle().length() < 3) {
                throw new UpdateEntityException("Title is not valid");
            }
            compilation.setTitle(compilationInDto.getTitle());
        }
        if (compilationInDto.getPinned() != null) {
            compilation.setPinned(compilationInDto.getPinned());
        }
        CompilationDto compilationDto = MapperCompilation
                .mapToCompilationDto(compilationsStorage.updateCompilation(compilation));
        return new ResponseEntity<>(compilationDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = compilationsStorage.getCompilations(Paging.paging(from, size));
        } else {
            compilations = compilationsStorage.getCompilations(pinned, Paging.paging(from, size));
        }
        return new ResponseEntity<>(compilations.stream()
                .map(MapperCompilation::mapToCompilationDto).collect(Collectors.toList()), HttpStatus.OK);

    }

    public ResponseEntity<Object> getCompilation(Integer compId) {
        return new ResponseEntity<>(MapperCompilation.mapToCompilationDto(compilationsStorage.getCompilation(compId))
                , HttpStatus.OK);
    }
}
