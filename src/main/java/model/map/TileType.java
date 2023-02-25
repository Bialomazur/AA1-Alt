package model.map;

import model.growable.PlantType;

import java.util.Collections;
import java.util.Set;

public enum TileType {
    FOREST(4, "Forest", "Fo", Set.of(PlantType.MUSHROOM, PlantType.CARROT)),
    LARGE_FOREST(8, "Large Forest", "LFo", Set.of(PlantType.MUSHROOM, PlantType.CARROT)),
    FIELD(4, "Field", "Fi", Set.of(PlantType.SALAD, PlantType.TOMATO, PlantType.CARROT)),
    LARGE_FIELD(8, "Large Field", "LFi", Set.of(PlantType.SALAD, PlantType.TOMATO, PlantType.CARROT)),
    GARDEN(2, "Garden", "G", Set.of(PlantType.values())),
    BARN(Double.POSITIVE_INFINITY, "Barn", "B", Set.of(PlantType.values())),
    EMPTY(0, "Empty", "E", Set.of());

    private final double capacity;
    private final String name;
    private final String nickName;
    private final Set<PlantType> allowedPlants;

    TileType(final double capacity, final String name, final String nickName, final Set<PlantType> allowedPlants) {
        this.capacity = capacity;
        this.name = name;
        this.nickName = nickName;
        this.allowedPlants = allowedPlants;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public String getName() {
        return this.name;
    }

    public String getNickName() {
        return this.nickName;
    }

    //TODO: Perhaps replace with a simple boolean returning whether the plant type is allowed or not?
    public Set<PlantType> getAllowedPlants() {
        return Collections.unmodifiableSet(this.allowedPlants);
    }
}
