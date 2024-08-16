package com.game.cities.persistent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sessions")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "current_city")
    private String currentCity;

    @Column(name = "active")
    private boolean active;

    @ManyToMany
    @JoinTable(
            name = "sessions_cities",
            joinColumns = @JoinColumn(name = "game_session_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "used_cities_id", referencedColumnName="id")
    )
    private Set<City> usedCities = new HashSet<>();
}
