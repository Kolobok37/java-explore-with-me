package com.ewmservice.event;

import com.ewmservice.event.client.EventClient;
import com.ewmservice.event.client.statsDto.AppDto;
import com.ewmservice.event.dto.EventFullDto;
import com.ewmservice.event.dto.EventShortDto;
import com.ewmservice.event.dto.MapperEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public abstract class EventService {

    @Autowired
    EventClient eventClient;


    public List<AppDto> getAllViewsByEvents(List<String> uris) {
        return eventClient.getStats(uris);
    }

    public List<EventFullDto> distributionViewByEvents(List<Event> events) {
        HashMap<Integer, Event> eventMap = new HashMap<>();
        List<EventFullDto> eventFullDto = new ArrayList<>();
        events.forEach(e -> eventMap.put(e.getId(), e));
        List<AppDto> view = getAllViewsByEvents(events.stream().map(e -> "/events/" + e.getId())
                .collect(Collectors.toList()));
        if (view.isEmpty()) {
            return events.stream().map(e -> MapperEvent.mapToEventFullDto(e, 0)).collect(Collectors.toList());
        }
        for (AppDto appDto : view) {
            int id = Integer.parseInt(appDto.getUri().substring(8));
            EventFullDto eventDto = MapperEvent.mapToEventFullDto(eventMap.get(id), appDto.getHits());
            eventFullDto.add(eventDto);
            eventMap.remove(id);
        }
        eventFullDto.addAll(eventMap.values().stream().map(e -> MapperEvent.mapToEventFullDto(e, 0))
                .collect(Collectors.toList()));
        return eventFullDto;
    }

    public List<EventShortDto> getShortDtoWithView(List<Event> events) {
        List<EventFullDto> eventFullDto = distributionViewByEvents(events);
        return eventFullDto.stream().map(MapperEvent::mapToEventShortDto).collect(Collectors.toList());
    }

}
