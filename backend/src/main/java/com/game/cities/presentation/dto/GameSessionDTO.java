package com.game.cities.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameSessionDTO {

    private Long id;

    private String currentCity;

    private boolean active;
}
