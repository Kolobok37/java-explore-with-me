package com.ewmservice.request;

import com.ewmservice.event.Event;
import com.ewmservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User requester;
    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;
    @Column(name = "created")
    LocalDateTime created;
    @Column(name = "status_request")
    String status;
}
