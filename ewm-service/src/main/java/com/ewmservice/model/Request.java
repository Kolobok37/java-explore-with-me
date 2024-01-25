package com.ewmservice.model;

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
    private    Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private    User requester;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private   Event event;
    @Column(name = "created")
    private   LocalDateTime created;
    @Column(name = "status_request")
    private   String status;
}
