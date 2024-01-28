package com.explore.exploreWithMe.service;


import com.explore.exploreWithMe.dto.AppDto;
import com.explore.exploreWithMe.dto.HitDto;
import com.explore.exploreWithMe.dto.MapperHit;
import com.explore.exploreWithMe.exception.ValidationDataException;
import com.explore.exploreWithMe.model.App;
import com.explore.exploreWithMe.model.Hit;
import com.explore.exploreWithMe.storage.StatisticsStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticService {
    @Autowired
    StatisticsStorage statisticsStorage;

    public ResponseEntity<List<AppDto>> getStats(String start, String end, List<String> uris, String unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<AppDto> appDtoList;
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (start == null && end != null || end == null && start != null) {
            throw new ValidationDataException("Date is not valid");
        }
        if (start == null || start.isBlank()) {
            startTime = LocalDateTime.of(1400, 1, 1, 0, 0);
        } else {
            startTime = LocalDateTime.parse(start, formatter);
        }
        if (end == null || end.isBlank()) {
            endTime = LocalDateTime.of(9999, 12, 31, 23, 59);
        } else {
            endTime = LocalDateTime.parse(end, formatter);
        }
        if (startTime.isAfter(endTime)) {
            throw new ValidationDataException("Date is not valid");
        }
        if (uris == null) {
            appDtoList = statisticsStorage.getAllStats(startTime, endTime, unique).stream()
                    .sorted(Comparator.comparingDouble(AppDto::getHits).reversed()).collect(Collectors.toList());
        } else {
            appDtoList = statisticsStorage.getStatsByUri(uris, startTime, endTime, unique).stream()
                    .sorted(Comparator.comparingDouble(AppDto::getHits).reversed()).collect(Collectors.toList());
        }
        return new ResponseEntity<>(appDtoList, HttpStatus.OK);
    }

    public ResponseEntity<Object> addHit(HitDto hitDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (hitDto.getName() == null) {
            hitDto.setName(hitDto.getUri());
        }
        if (hitDto.getTimestamp() == null) {
            hitDto.setTimestamp(LocalDateTime.now().format(formatter));
        }
        Hit hit = MapperHit.mapToHit(hitDto);

        App app = hit.getApp();
        statisticsStorage.createApp(app);
        return new ResponseEntity<>(statisticsStorage.addHit(hit), HttpStatus.CREATED);
    }


}
