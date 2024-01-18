package com.explore.exploreWithMe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Entity
@Table(name = "app")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class App {
    @Id
    @Column(name = "hit_uri")
    @NotEmpty(message = "Uri is not be empty")
    private String uri;

    @Column(name = "name")
    @NotEmpty(message = "Name app is not be empty")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hit_uri")
    private List<Hit> hit;


}
