package com.ewmservice.storage.jpa;

import com.ewmservice.model.Event;
import com.ewmservice.model.Request;
import com.ewmservice.model.auxiliaryEntities.StatusRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Integer> {

    List<Request> findAllByRequester(Integer userId);

    List<Request> findAllByEvent(Integer eventId);

    @Query("FROM Request as r " +
            "WHERE r.id = ?1 AND " +
            "r.status in ?2")
    List<Request> findAllByIdAndStatus(List<Integer>  Id, String status);
}
