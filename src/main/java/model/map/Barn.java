package model.map;

import model.growable.Growable;
import model.growable.PlantType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Barn extends Tile {
    private final Set<Growable> growables = new HashSet<>();
    private static final int MIN_UNITS_STORED = 0;
    private static final int MIN_STORABLE_UNITS = 1;
    private static final int DEFAULT_UNITS_STORED = 1;

    public void spoil() {
        this.growables.clear();
    }

    public boolean isEmpty() {
        return growables.isEmpty();
    }

    private void addUnitsToExistingGrowable(final Growable growable, final int amount) {
        for (Growable barnGrowable : this.growables) {
            if (barnGrowable.getPlantType() == growable.getPlantType()) {
                barnGrowable.setPopulation(barnGrowable.getPopulation() + amount);
                return;
            }
        }
    }

    private boolean hasGrowable(final Growable growable) {
        for (Growable barnGrowable : this.growables) {
            if (barnGrowable.getPlantType() == growable.getPlantType()) {
                return true;
            }
        }
        return false;
    }

    private Growable getStoredGrowable(final Growable growable) {
        for (Growable barnGrowable : this.growables) {
            if (barnGrowable.getPlantType() == growable.getPlantType()) {
                return barnGrowable;
            }
        }
        return null;
    }

    public void storeGrowable(final Growable growable, final int amount) {
        if (growable.getPopulation() < MIN_STORABLE_UNITS) {
            throw new IllegalArgumentException("Cannot store a Growable with a population of less than 1.");
        }

        if (growable.getPopulation() < amount) {
            throw new IllegalArgumentException("Cannot store more units than the Growable has.");
        }

        if (hasGrowable(growable)) {
            addUnitsToExistingGrowable(growable, amount);
        } else {
            this.growables.add(new Growable(growable.getPlantType(), amount));
        }

        growable.setPopulation(growable.getPopulation() - amount);
    }

    // TODO: Make sure that if the player sells multiple growables, ALL of them are FIRST removed from the barn and THEN added to the player's gold amount.
    // TODO: This is to ensure the atomicity of the market transaction, that if an exception is thrown, the player's gold amount is not changed.
    // TODO: Consider using PlantType instead of Growable as the parameter.
    public void removeGrowable(final Growable growable, final int amount) {
        if (amount < MIN_UNITS_STORED) {
            throw new IllegalArgumentException("Cannot remove a negative amount of units.");
        }
        if (!hasGrowable(growable)) {
            throw new IllegalArgumentException("Cannot remove a Growable that is not in the barn.");
        }

        Growable storedGrowable = getStoredGrowable(growable);  //TODO: Consider creating an empty Growable object receive from getStoredGrowable when the Growable is not in the barn.
        if (storedGrowable.getPopulation() < amount) {
            throw new IllegalArgumentException("Cannot remove more units than the barn has.");
        }

        storedGrowable.setPopulation(storedGrowable.getPopulation() - amount);

        if (storedGrowable.getPopulation() == MIN_UNITS_STORED) {
            this.growables.remove(storedGrowable);
        }
    }

    public Set<Growable> getInventory() {
        return Collections.unmodifiableSet(this.growables);
    }

    // TODO: Enhance for production
    public void init() {
        for (PlantType plantType : PlantType.values()) {
            Growable growable = new Growable(plantType, DEFAULT_UNITS_STORED);
            this.growables.add(growable);
        }
    }

    public int getTotalUnitsStored() {
        int totalUnitsStored = 0;
        for (Growable growable : this.growables) {
            totalUnitsStored += growable.getPopulation();
        }
        return totalUnitsStored;
    }
}
