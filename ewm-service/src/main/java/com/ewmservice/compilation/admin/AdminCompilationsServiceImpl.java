package com.ewmservice.compilation.admin;

import com.ewmservice.compilation.Compilation;
import com.ewmservice.compilation.dto.CompilationDto;
import com.ewmservice.compilation.dto.CompilationInDto;
import com.ewmservice.compilation.dto.CompilationUpdateDto;
import com.ewmservice.compilation.dto.MapperCompilation;
import com.ewmservice.compilation.storage.CompilationsStorage;
import com.ewmservice.event.Event;
import com.ewmservice.event.storage.EventStorage;
import com.ewmservice.exception.DeleteCompilationException;
import com.ewmservice.exception.UpdateEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminCompilationsServiceImpl implements AdminCompilationsService {
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

}
