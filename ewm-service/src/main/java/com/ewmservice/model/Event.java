package com.ewmservice.model;

import com.ewmservice.model.auxiliaryEntities.Location;
import com.ewmservice.model.auxiliaryEntities.StateEvent;
import com.ewmservice.model.auxiliaryEntities.StatusRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private    Integer id;
    @Column(name = "title")
    private   String title;
    @Column(name = "annotation")
    private    String annotation;
    @Column(name = "description")
    private     String description;
    @Column(name = "paid")                      //Платно?
    private    Boolean paid;
    @Column(name = "request_moderation")
    private     Boolean requestModeration;                  //Нужно подтверждать?
    @Column(name = "event_date")
    private   LocalDateTime eventDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private     List<Request> requests;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private    Category category;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private  User initiator;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private    Location location;
    @Column(name = "participant_limit")
    private    Integer participantLimit;
    @Column(name = "state_event")
    private     StateEvent stateAction;
    @Column(name = "created_on")
    private    LocalDateTime createdOn;
    @Column(name = "published_on")
    private   LocalDateTime publishedOn;

    public List<Request> getApprovedRequest() {
        return requests.stream().filter(r -> StatusRequest.CONFIRMED.toString().equals(r.getStatus())).collect(Collectors.toList());
    }
}
