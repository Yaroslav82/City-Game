package com.game.cities.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CityScraper {

    private final static String RESOURCE_URL = "https://uk.m.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0_%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B8_(%D0%B7%D0%B0_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D1%96%D1%82%D0%BE%D0%BC)";
    private final static String ERROR_MESSAGE = "Resource %s unavailable. Check the URL is correct";

    private final static String cssQuery = "table.wikitable tbody tr td:nth-child(2) a";

    public static List<String> scrapeCities() {
        try {
            Document doc = Jsoup.connect(RESOURCE_URL).get();
            Elements cityElements = doc.select(cssQuery);

            List<String> cities = new ArrayList<>();
            for (Element cityElement : cityElements) {
                String city = cityElement.text();
                cities.add(city);
            }

            return cities;
        } catch (Exception e) {
            log.error(ERROR_MESSAGE.formatted(RESOURCE_URL));
            System.exit(-1);
        }
        return null;
    }
}
