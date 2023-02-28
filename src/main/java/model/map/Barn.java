package model.map;

import model.event.Event;
import model.event.EventType;
import model.growable.Growable;
import model.growable.PlantType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Barn extends Tile{
    private static final String GROWABLE_NOT_IN_STORE = "Cannot take a growable that is not stored.";
    private static final String NOT_ENOUGH_UNITS_STORED = "Not enough %s stored.";
    private static final int ROUNDS_UNTIL_SPOIL = 6;
    private final Set<Growable> storage = new HashSet<>();
    private static final int INITIAL_STORAGE_QUANTITIES = 1;

    public Barn(final int xCoordinate, final int yCoordinate) {
        super(xCoordinate, yCoordinate, Biotope.BARN);
    }

    private Growable getStoredGrowable(final Growable growable) {
        for (final Growable barnGrowable : this.storage) {
            if (barnGrowable.equals(growable)) {
                return barnGrowable;
            }
        }
        throw new IllegalArgumentException(GROWABLE_NOT_IN_STORE);
    }

    public Set<Growable> getAllStoredGrowables() {
        return Collections.unmodifiableSet(this.storage);
    }

    @Override
    public void putGrowable(final Growable growable) {
        if (this.storage.contains(growable)) {
            final int unitsInStoree = this.getStoredGrowable(growable).getPopulation() + growable.getPopulation();
            this.getStoredGrowable(growable).setPopulation(unitsInStoree);
        } else {
            this.storage.add(new Growable(growable));
        }
        growable.kill();
    }

    @Override
    public Growable takeGrowable(final Growable growable, final int quantity) {
        final Growable storedGrowable = this.getStoredGrowable(growable);
        if (storedGrowable.getPopulation() < quantity) {
            throw new IllegalArgumentException(NOT_ENOUGH_UNITS_STORED.formatted(growable.getPluralName()));
        }
        storedGrowable.setPopulation(storedGrowable.getPopulation() - quantity);

        if (!storedGrowable.isAlive()) {
            this.storage.remove(storedGrowable);
        }

        if (this.storage.isEmpty()) {
            this.setRoundsPassedUntilUpdateAction(INITIAL_ROUNDS_PASSED);
        }

        return new Growable(growable.getPlantType(), quantity);
    }

    @Override
    public int getGrowablePopulation() {
        int totalPopulation = 0;

        for (final Growable growable : this.storage) {
            totalPopulation += growable.getPopulation();
        }

        return totalPopulation;
    }

    @Override
    public boolean isEmpty() {
        return this.storage.isEmpty();
    }


    //DEBUG: REFACTOR
    @Override
    public Event update() {
        if (this.active()) {
            this.setRoundsPassedUntilUpdateAction(this.getRoundsPassedUntilUpdateAction() + ROUNDS_PASSED_INCREMENT);
        }

        if (this.getRoundsPassedUntilUpdateAction() == ROUNDS_UNTIL_SPOIL) {
            final int growablesToSpoil = this.getGrowablePopulation();
            this.spoil();
            this.setRoundsPassedUntilUpdateAction(INITIAL_ROUNDS_PASSED);
            return new Event(EventType.BARN_HAS_SPOILED, growablesToSpoil);
        }

        return new Event();
    }

    public void spoil() {
        this.storage.clear();
    }

    @Override
    public boolean active() {
        return !this.storage.isEmpty();
    }

    public void init() {
        for (final PlantType plantType : PlantType.values()) {
            this.storage.add(new Growable(plantType, INITIAL_STORAGE_QUANTITIES));
        }
    }

    //DEBUG: REFACTOR
    @Override
    public int getUpdatesLeftUntilAction() {
        return ROUNDS_UNTIL_SPOIL - this.getRoundsPassedUntilUpdateAction();
    }

    @Override
    public String toString() {
        final String countdown = this.active() ? this.getUpdatesLeftUntilAction() + " " : "  ";
        return "     " + "\n" + " B " + countdown + "\n" + "     ";
    }
}
