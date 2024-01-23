package com.ewmservice.storage.jpa;

import com.ewmservice.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {


    @Query("FROM Request as r " +
            "WHERE r.id in ?1 AND " +
            "r.status in ?2")
    List<Request> findAllByIdAndStatus(List<Integer> id, String status);

    List<Request> findAllByRequesterId(Integer userId);

    List<Request> findAllByEventId(Integer eventId);
}
