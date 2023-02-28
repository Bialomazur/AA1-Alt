package model.map;

import model.event.Event;
import model.event.EventType;
import model.growable.Growable;


public class CultivableTile extends Tile{
    private Growable plantedGrowable;
    private static final String TILE_ALREADY_CULTIVATED = "Tile already cultivated.";
    private static final String TILE_NOT_CULTIVATED = "Tile not cultivated.";
    private static final String NO_SUCH_GROWABLE_PLANTED = "No such growable planted on this tile.";
    private static final String TOO_LITTLE_POPULATION = "Tile only has a population of %d.";

    private static final int MIN_CULTIAVED_POPULATION = 0; //TODO: Explain why this is not a dry violation with @Growable#MIN_POPULATION!


    public CultivableTile(final Biotope biotope, final int xCoordinate, final int yCoordinate) {
        super(xCoordinate, yCoordinate, biotope);
    }

    @Override
    public void putGrowable(final Growable growable) {
        if (this.isEmpty()) {
            throw new IllegalArgumentException(TILE_ALREADY_CULTIVATED);
        }
        this.plantedGrowable = new Growable(growable);
        growable.kill();
        this.setRoundsPassedUntilUpdateAction(INITIAL_ROUNDS_PASSED);
    }

    @Override
    public Growable takeGrowable(final Growable growable, final int quantity) {
        if (!this.isEmpty()) {
            throw new IllegalArgumentException(TILE_NOT_CULTIVATED);
        }
        if (!growable.equals(this.plantedGrowable)) {
            throw new IllegalArgumentException(NO_SUCH_GROWABLE_PLANTED);
        }
        if (this.plantedGrowable.getPopulation() < quantity) {
            throw new IllegalArgumentException(TOO_LITTLE_POPULATION.formatted(this.plantedGrowable.getPopulation()));
        }

        final Growable takenGrowable = new Growable(growable.getPlantType(), quantity);

        if (this.plantedGrowable.getPopulation() == quantity) {
            this.plantedGrowable = null;
            this.setRoundsPassedUntilUpdateAction(INITIAL_ROUNDS_PASSED);
        } else {
            this.plantedGrowable.setPopulation(this.plantedGrowable.getPopulation() - quantity);
        }
        return takenGrowable;
    }

    @Override
    public boolean isEmpty() {
        return this.plantedGrowable == null;
    }



    @Override
    public int getGrowablePopulation() {
        if (this.isEmpty()) {
            return this.plantedGrowable.getPopulation();
        }

        return MIN_CULTIAVED_POPULATION;
    }

    @Override
    public Event update() {
        final Event event = new Event();
        if (!this.active()) {
            return event;
        }
        this.setRoundsPassedUntilUpdateAction(this.getRoundsPassedUntilUpdateAction() - ROUNDS_PASSED_INCREMENT);
        final int populationPreGrowth = this.plantedGrowable.getPopulation();

        if (this.getRoundsPassedUntilUpdateAction() == this.plantedGrowable.getGrowthInterval()) {
            this.plantedGrowable.grow();
            event.setEventType(EventType.GROWABLE_POPULATION_INCREASED);
            event.setImpact(this.plantedGrowable.getPopulation() - populationPreGrowth);
            this.setRoundsPassedUntilUpdateAction(INITIAL_ROUNDS_PASSED);
        }

        if (this.plantedGrowable.getPopulation() >= this.getBiotope().getCapacity()) {
            this.plantedGrowable.setPopulation((int) this.getBiotope().getCapacity());
            event.setImpact((int) this.getBiotope().getCapacity() - populationPreGrowth);
        }
        return event;
    }

    @Override
    public boolean active() {
        return !this.isEmpty() && this.plantedGrowable.getPopulation() < this.getBiotope().getCapacity();
    }

    @Override
    public int getRoundsPassedUntilUpdateAction() {
        return this.getRoundsPassedUntilUpdateAction();
    }

    @Override
    public int getUpdatesLeftUntilAction() {
        if (this.isEmpty()) {
            return 0;
        } else {
            return this.plantedGrowable.getGrowthInterval() - this.getRoundsPassedUntilUpdateAction();
        }
    }

    @Override
    public String toString() {
        if (this.getBiotope() == Biotope.EMPTY) {
            return "      \n      \n      ";
        }

        final StringBuilder sb = new StringBuilder();

        final int currentPopulation = this.isEmpty() ? 0 : this.plantedGrowable.getPopulation();
        final String headerTemplate = "%s %s";
        final String headerPostfix = this.active() ? "" + this.getUpdatesLeftUntilAction() : "*";

        String header = headerTemplate.formatted(this.getBiotope().getNickName(), headerPostfix);
        final String mid = this.isEmpty() ? "     " : "  " + this.plantedGrowable.getPlantType().getNickName() + "  ";
        final String footer = " " + currentPopulation + "/" + (int) this.getBiotope().getCapacity()  + " ";

        if (header.length() < 5) {
            header = " " + header;
        }

        if (header.length() < 5) {
            header += " ";
        }

        return header + "\n" + mid + "\n" + footer;
    }

}
