package com.ewmservice.event;

import com.ewmservice.category.Category;
import com.ewmservice.event.auxiliaryEntities.Location;
import com.ewmservice.event.auxiliaryEntities.StateEvent;
import com.ewmservice.request.Request;
import com.ewmservice.request.StatusRequest;
import com.ewmservice.user.User;
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
    Integer id;
    @Column(name = "title")
    String title;
    @Column(name = "annotation")
    String annotation;
    @Column(name = "description")
    String description;
    @Column(name = "paid")
    Boolean paid;
    @Column(name = "request_moderation")
    Boolean requestModeration;
    @Column(name = "event_date")
    LocalDateTime eventDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    List<Request> requests;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    User initiator;
    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;
    @Column(name = "participant_limit")
    Integer participantLimit;
    @Column(name = "state_event")
    StateEvent stateAction;
    @Column(name = "created_on")
    LocalDateTime createdOn;
    @Column(name = "published_on")
    LocalDateTime publishedOn;

    public List<Request> getApprovedRequest() {
        return requests.stream().filter(r -> StatusRequest.CONFIRMED.toString().equals(r.getStatus())).collect(Collectors.toList());
    }
}
