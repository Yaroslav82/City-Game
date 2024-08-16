package com.game.cities.service;

import com.game.cities.persistent.entity.City;
import com.game.cities.persistent.entity.GameSession;
import com.game.cities.persistent.repository.GameSessionRepository;
import com.game.cities.presentation.dto.GameSessionDTO;
import com.game.cities.presentation.exceptions.SessionNotFoundException;
import com.game.cities.presentation.exceptions.WrongCityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GameService {

    @Autowired
    private CityService cityService;

    @Autowired
    private GameSessionRepository sessionRepository;

    private final static String WRONG_LETTER_TEXT = "Місто повинно починатися на літеру %s";
    private final static String USED_CITY_TEXT = "Місто %s вже було використано";

    public GameSessionDTO startGame() {
        City city = cityService.chooseRandomCity(cityService.findAll());
        GameSession gameSession = new GameSession();
        gameSession.setCurrentCity(city.getName());
        gameSession.getUsedCities().add(city);
        gameSession.setActive(true);

        return gameSessionToDTO(sessionRepository.save(gameSession));
    }

    public void endGame(Long sessionId) {
        GameSession gameSession = getSessionById(sessionId);
        gameSession.setActive(false);
        sessionRepository.save(gameSession);
    }

    public GameSessionDTO nextCity(String cityName, Long sessionId) {
        GameSession gameSession = getSessionById(sessionId);
        Set<City> usedCities = gameSession.getUsedCities();

        City playerCity = processPlayerCity(cityName, gameSession);

        usedCities.add(playerCity);

        List<City> possibleCities = cityService.findAllPossibleCities(cityName);
        possibleCities.removeAll(usedCities);

        if (possibleCities.isEmpty()) {
            gameSession.setActive(false);

            return gameSessionToDTO(sessionRepository.save(gameSession));
        }

        City nextCity = cityService.chooseRandomCity(possibleCities);
        gameSession.setCurrentCity(nextCity.getName());
        usedCities.add(nextCity);

        return gameSessionToDTO(sessionRepository.save(gameSession));
    }

    private City processPlayerCity(String cityName, GameSession gameSession) {
        Set<City> usedCities = gameSession.getUsedCities();
        char lastLetter = cityService.getNextLetter(gameSession.getCurrentCity());

        if (cityName.toLowerCase().charAt(0) != lastLetter) {
            throw new WrongCityException(WRONG_LETTER_TEXT.formatted(lastLetter));
        }

        if (usedCities.stream().anyMatch(city -> city.getName().equalsIgnoreCase(cityName))) {
            throw new WrongCityException(USED_CITY_TEXT.formatted(cityName));
        }

        return cityService.findByName(cityName);
    }

    private GameSession getSessionById(Long sessionId) {
        return sessionRepository.findByIdAndActive(sessionId, true)
                .orElseThrow(SessionNotFoundException::new);
    }

    private GameSessionDTO gameSessionToDTO(GameSession session) {
        return new GameSessionDTO(session.getId(), session.getCurrentCity(), session.isActive());
    }
}
