package com.ewmservice.comment;

import com.ewmservice.event.Event;
import com.ewmservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "commentator_id")
    private User commentator;
    @Column(name = "date_comment", nullable = false)
    LocalDateTime created;
    @NotBlank(message = "Text cannot be empty")
    @Column(name = "date_comment", nullable = false)
    private String text;
}