package com.evolutionnext.lazyconstant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LazyMapTest {
    @SuppressWarnings("preview")
    @Test
    void testStableMap() {
        Map<Planet, PlanetData> planetMap = Map.ofLazy
            (EnumSet.allOf(Planet.class), PlanetLoader::load);

        PlanetData planetData = planetMap.get(Planet.EARTH);
        Assertions.assertThat(planetData.moons()).isOne();

        System.out.println(planetMap);

        PlanetData planetData2 = planetMap.get(Planet.EARTH);
    }

    @Test
    void testStableMapWithFunctionalMap() {
        Map<Planet, PlanetData> planetMap = Map.ofLazy
            (EnumSet.allOf(Planet.class), PlanetLoader::load);

        Set<String> allPlanetColors =
            Stream.of(Planet.values())
                .map(planetMap::get)
                .map(PlanetData::color)
                .collect(Collectors.toSet());

        Set<String> zeroMoonsPlanetsSet = Stream.of(Planet.values())
            .map(planetMap::get)
            .filter(planetData -> planetData.moons() == 0)
            .map(PlanetData::name)
            .collect(Collectors.toSet());

        System.out.printf("All Planet Colors: %s%n", allPlanetColors);
        System.out.printf("Zero Moons Planets: %s%n", zeroMoonsPlanetsSet);
    }
}
