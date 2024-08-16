package com.game.cities.service;

import com.game.cities.persistent.entity.City;
import com.game.cities.persistent.repository.CityRepository;
import com.game.cities.presentation.exceptions.WrongCityException;
import com.game.cities.service.utils.CityScraper;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    private final static String NO_SUCH_CITY_TEXT = "Міста з таким ім'ям не існує";

    private final static List<Character> forbiddenLetters = List.of('ь', 'ъ', 'и', 'й');

    @PostConstruct
    @Transactional
    private void loadCitiesToDatabase() {
        if (cityRepository.count() == 0) {
            List<String> cityNames = CityScraper.scrapeCities();
            List<City> cities = cityNames.stream().map(City::new).toList();
            cityRepository.saveAllAndFlush(cities);
        }
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public List<City> findAllPossibleCities(String cityName) {
        char letter = getNextLetter(cityName);
        return cityRepository.findAllByNameStartsWithIgnoreCase(String.valueOf(letter));
    }

    public City findByName(String cityName) {
        return cityRepository.findByName(cityName)
                .orElseThrow(() -> new WrongCityException(NO_SUCH_CITY_TEXT));
    }

    public char getNextLetter(String cityName) {
        char letter = cityName.charAt(cityName.length() - 1);
        if (forbiddenLetters.contains(letter)) {
            letter = cityName.charAt(cityName.length() - 2);
        }
        return letter;
    }

    public City chooseRandomCity(List<City> cities) {
        return cities.get(new Random().nextInt(cities.size()));
    }
}
