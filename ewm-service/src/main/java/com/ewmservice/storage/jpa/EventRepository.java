package com.ewmservice.storage.jpa;

import com.ewmservice.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "Select *" +
            "FROM events AS ev " +
            "JOIN categories AS c ON ev.category_id = c.id " +
            "JOIN users AS us ON ev.initiator_id = us.id " +
            "WHERE us.id = :id ", nativeQuery = true)
    List<Event> findByInitiatorById(Integer id, Pageable pageable);

    @Query("FROM Event AS ev " +
            "JOIN Category AS c ON ev.category = c.id " +
            "JOIN User AS us ON ev.initiator = us.id " +
            "WHERE us.id in :users  AND "+
            "c.id in :categories AND " +
            "event_date BETWEEN :rangeStart AND :rangeEnd")
    List<Event> getEventsByAdmin(Integer[] users,Integer[] categories,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable paging);

    @Query("FROM Event AS ev " +
            "JOIN Category AS c ON ev.category = c.id " +
            "JOIN User AS us ON ev.initiator = us.id " +
            "WHERE ev.paid = :paid  AND "+
            "ev.annotation in :text OR ev.description in :text AND "+
            "c.id in :categories AND " +
            "event_date BETWEEN :startTime AND :endTime")
    List<Event> getEventsByPublic(String text, Integer[] categories, LocalDateTime startTime,
                                  LocalDateTime endTime, Boolean paid, Pageable paging);
}
