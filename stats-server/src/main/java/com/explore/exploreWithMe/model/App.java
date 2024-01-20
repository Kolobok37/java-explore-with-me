package com.explore.exploreWithMe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "app")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class App {
    @Id
    @Column(name = "uri")
    private String uri;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinColumn(name = "hit_uri")
    private List<Hit> hit;


}
