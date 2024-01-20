package com.explore.exploreWithMe.storage;


import com.explore.exploreWithMe.dto.AppDto;
import com.explore.exploreWithMe.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface AppRepository extends JpaRepository<App, String> {


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO app(name,uri) " +
            "VALUES(:name,:uri) " +
            "ON CONFLICT (uri) " +
            "DO NOTHING",
            nativeQuery = true)
    void upsertApp(String name, String uri);

    @Query("Select new com.explore.exploreWithMe.dto.AppDto(a.uri, a.name, count(h.ip) ) " +
            "FROM Hit AS h " +
            "JOIN App AS a ON h.app= a.uri " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY a.name,a.uri ")
    List<AppDto> getStatsAllUri(LocalDateTime start, LocalDateTime end);

    @Query("Select new com.explore.exploreWithMe.dto.AppDto(a.uri, a.name, count(DISTINCT h.ip) ) " +
            "FROM Hit AS h " +
            "JOIN App AS a ON h.app= a.uri " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY a.name,a.uri ")
    List<AppDto> getStatsAllUriForUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query("Select new com.explore.exploreWithMe.dto.AppDto(a.uri, a.name, count(h.ip) ) " +
            "FROM Hit AS h " +
            "JOIN App AS a ON h.app= a.uri " +
            "WHERE h.timestamp BETWEEN :start AND :end AND " +
            "a.name in (:uri) " +
            "GROUP BY a.name,a.uri ")
    List<AppDto> getStatsByUriByAllIp(List<String> uri, LocalDateTime start, LocalDateTime end);

    @Query("Select new com.explore.exploreWithMe.dto.AppDto(a.uri, a.name, count(DISTINCT h.ip) ) " +
            "FROM Hit AS h " +
            "JOIN App AS a ON h.app= a.uri " +
            "WHERE h.timestamp BETWEEN :start AND :end AND " +
            "a.name in (:uri) " +
            "GROUP BY a.name,a.uri ")
    List<AppDto> getStatsByUriByUniqueIp(List<String> uri, LocalDateTime start, LocalDateTime end);
}