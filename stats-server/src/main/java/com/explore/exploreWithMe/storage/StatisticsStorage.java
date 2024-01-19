package com.explore.exploreWithMe.storage;


import com.explore.exploreWithMe.model.App;
import com.explore.exploreWithMe.model.Hit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor

public class StatisticsStorage {
    AppRepository appRepository;

    HitRepository hitRepository;

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void createApp(App app) {
        appRepository.upsertApp(app.getName(), app.getUri());
    }

    public Hit addHit(Hit hit) {
        return hitRepository.save(hit);
    }

    public List<App> getStatsByUri(List<String> uris, LocalDateTime start, LocalDateTime end, String unique) {
        List<App> app;
        if (unique != null && unique.equals("true")) {
            app = appRepository.getStatsAllUriForUniqueIp(start, end).stream()
                    .peek(a -> a.setHit(filterUniqueIp(a.getHit())))
                    .collect(Collectors.toList());
        } else {
            app = appRepository.getStatsByUriByAllIp(uris, start, end);
        }
        return app;
    }

    public List<App> getAllStats(List<String> uris, LocalDateTime start, LocalDateTime end, String unique) {
        List<App> app;
        if (unique != null && unique.equals("true")) {
            app = appRepository.getStatsAllUriForUniqueIp(start, end).stream()
                    .peek(a -> a.setHit(filterUniqueIp(a.getHit())))
                    .collect(Collectors.toList());
        } else {
            app = appRepository.getStatsAllUri(start, end);
        }
        return app;
    }

    public List<Hit> filterUniqueIp(List<Hit> hit) {
        return hit.stream()
                .filter(distinctByKey(Hit::getIp))
                .collect(Collectors.toList());
    }
}