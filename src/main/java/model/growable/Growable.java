package model.growable;


import java.util.Objects;

/**
 * class modelling a growable object.
 * <p>
 * This strong degree of abstraction from a mere "vegetable" to a "growable" is essential,
 * in order to allow for the possibility of building on top of the massive diversity in characteristics and behavior
 * inside of a given plant-type in the future.
 * <p>
 * Furthermore given that for instance a mushroom is a fungus, a tomato is a fruit, and a carrot is a root,
 * those organisms are not only different in their type of species, appearance, but also in their behavior,
 * and therefore longterm, the requirement for dynamically implemented distinctions between those,
 * isn't merely probable, but inevitable.
 *
 * @author uejxk
 * @version 1.0
 */

public class Growable {
    private final PlantType plantType;
    private int population;
    private static final int GROWTH_FACTOR = 2;
    private static final int MIN_POPULATION = 0;
    private static final String POPULATION_CANNOT_BE_LESS_THAN = "Population cannot be less than %d.";

    public void setPopulation(final int population) {
        if (population < MIN_POPULATION) {
            throw new IllegalArgumentException(POPULATION_CANNOT_BE_LESS_THAN.formatted(MIN_POPULATION));
        }
        this.population = population;
    }

    public int getPopulation() {
        return this.population;
    }

    public PlantType getPlantType() {
        return this.plantType;
    }

    public Growable(final Growable growable) {
        this.plantType = growable.getPlantType();
        this.population = growable.getPopulation();
    }

    public Growable(final PlantType plantType, final int population) {
        this.plantType = plantType;
        this.population = population;
    }

    public void grow() {
        this.population *= GROWTH_FACTOR;
    }

    public int getGrowthInterval() {
        return this.plantType.getGrowthInterval();
    }


    //TODO: Verify that this is the correct way to implement equals and hashcode.
    //TODO: Add that this is auto-generated if happens to be used.
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Growable growable = (Growable) o;
        return this.plantType == growable.plantType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.plantType);
    }

    public String getPluralName() {
        return this.plantType.getPluralName();
    }

    public String getSingularName() {
        return this.plantType.getSingularName();
    }

    public String getNickName() {
        return this.plantType.getNickName();
    }

    public void kill() {
        this.population = MIN_POPULATION;
    }

    public boolean isAlive() {
        return this.population > MIN_POPULATION;
    }
}
