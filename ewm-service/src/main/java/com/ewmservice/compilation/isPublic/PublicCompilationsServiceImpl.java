package com.ewmservice.compilation.isPublic;

import com.ewmservice.Paging;
import com.ewmservice.compilation.Compilation;
import com.ewmservice.compilation.dto.CompilationDto;
import com.ewmservice.compilation.dto.MapperCompilation;
import com.ewmservice.compilation.storage.CompilationsStorage;
import com.ewmservice.event.Event;
import com.ewmservice.event.dto.EventShortDto;
import com.ewmservice.event.isPublic.PublicEventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PublicCompilationsServiceImpl {
    @Autowired
    CompilationsStorage compilationsStorage;

    @Autowired
    PublicEventServiceImpl eventService;

    public ResponseEntity<Object> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations;
        List<Event> events = new ArrayList<>();
        Map<Integer, CompilationDto> compilationsDto = new HashMap<>();
        Map<Integer, EventShortDto> eventsDto = new HashMap<>();
        if (pinned == null) {
            compilations = compilationsStorage.getCompilations(Paging.paging(from, size));
        } else {
            compilations = compilationsStorage.getCompilations(pinned, Paging.paging(from, size));
        }
        compilations.stream().map(MapperCompilation::mapToCompilationDtoWithoutEvent)
                .forEach(c -> compilationsDto.put(c.getId(), c));
        for (Compilation com : compilations) {
            if (com.getEvents() != null) {
                events.addAll(com.getEvents());
            }
        }
        eventService.getShortDtoWithView(events).forEach(e -> eventsDto.put(e.getId(), e));
        for (Compilation com : compilations) {
            if (com.getEvents() != null) {
                List<Integer> idEvent = com.getEvents().stream().map(Event::getId).collect(Collectors.toList());
                for (Integer id : idEvent) {
                    compilationsDto.get(com.getId()).getEvents().add(eventsDto.get(id));
                }
            }
        }

        return new ResponseEntity<>(compilationsDto.values(), HttpStatus.OK);

    }

    public ResponseEntity<Object> getCompilation(Integer compId) {
        Compilation compilation = compilationsStorage.getCompilation(compId);
        CompilationDto compilationDto = MapperCompilation.mapToCompilationDtoWithoutEvent(compilation);
        if (!compilation.getEvents().isEmpty()) {
            compilationDto.setEvents(eventService.getShortDtoWithView(compilation.getEvents()));
        }
        return new ResponseEntity<>(compilationDto,
                HttpStatus.OK);
    }
}
