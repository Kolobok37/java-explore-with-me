package com.explore.exploreWithMe.storage;


import com.explore.exploreWithMe.dto.AppDto;
import com.explore.exploreWithMe.model.App;
import com.explore.exploreWithMe.model.Hit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor

public class StatisticsStorage {
    AppRepository appRepository;

    HitRepository hitRepository;

    public void createApp(App app) {
        appRepository.upsertApp(app.getName(), app.getUri());
    }

    public Hit addHit(Hit hit) {
        return hitRepository.save(hit);
    }

    public List<AppDto> getStatsByUri(List<String> uris, LocalDateTime start, LocalDateTime end, String unique) {
        List<AppDto> app;
        if (unique != null && unique.equals("true")) {
            app = appRepository.getStatsByUriByUniqueIp(uris, start, end);
        } else {
            app = appRepository.getStatsByUriByAllIp(uris, start, end);
        }
        return app;
    }

    public List<AppDto> getAllStats(LocalDateTime start, LocalDateTime end, String unique) {
        List<AppDto> app;
        if (unique != null && unique.equals("true")) {
            app = appRepository.getStatsAllUriForUniqueIp(start, end);
        } else {
            app = appRepository.getStatsAllUri(start, end);
        }
        return app;
    }
}