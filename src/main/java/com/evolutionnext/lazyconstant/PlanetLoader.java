package com.evolutionnext.lazyconstant;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class PlanetLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static PlanetData load(Planet planet) {
        try {
            System.out.printf("Loading data for %s, please wait%n", planet);

            Thread.sleep(4000);

            List<PlanetData> planetDataList = mapper.readValue(
                PlanetLoader.class.getResourceAsStream("/planets.json"),
                new TypeReference<>() {
                }
            );

            return planetDataList.stream()
                .filter(planetData ->
                    planetData.name().equals(Planet.capitalize(planet.name())))
                .findFirst()
                .orElseThrow();

        } catch (Exception e) {
            throw new RuntimeException("Failed to load planet data", e);
        }
    }
}
