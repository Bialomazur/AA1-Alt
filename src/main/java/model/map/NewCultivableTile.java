package model.map;

import model.growable.Growable;


public class NewCultivableTile extends NewTile{
    private Growable plantedGrowable;
    private boolean cultivated;

    private static final String TILE_ALREADY_CULTIVATED = "Tile already cultivated.";
    private static final String TILE_NOT_CULTIVATED = "Tile not cultivated.";
    private static final String NO_SUCH_GROWABLE = "No such growable cultivated on this tile.";
    private static final String TOO_LITTLE_POPULATION = "Tile only has a population of %d.";

    private static final int MIN_CULTIAVED_POPULATION = 0; //TODO: Explain why this is not a dry violation with @Growable#MIN_POPULATION!

    protected NewCultivableTile(final int x, final int y) {
        super(x, y);
    }

    @Override
    public void putGrowable(final Growable growable) {
        if (this.cultivated) {
            throw new IllegalArgumentException(TILE_ALREADY_CULTIVATED);
        }
        this.plantedGrowable = new Growable(growable);
        growable.kill();
    }

    @Override
    public Growable takeGrowable(final Growable growable, final int quantity) {
        if (!this.cultivated) {
            throw new IllegalArgumentException(TILE_NOT_CULTIVATED);
        }

        if (!growable.equals(this.plantedGrowable)) {
            throw new IllegalArgumentException(NO_SUCH_GROWABLE);
        }

        if (this.plantedGrowable.getPopulation() < quantity) {
            throw new IllegalArgumentException(TOO_LITTLE_POPULATION.formatted(this.plantedGrowable.getPopulation()));
        }

        this.plantedGrowable.setPopulation(this.plantedGrowable.getPopulation() - quantity);
        this.checkIfGrowableAlive();
        return new Growable(growable.getPlantType(), quantity);
    }

    //TODO: Remember to rename after after renaming @Growable#isAlive()!
    private void checkIfGrowableAlive() {
        if (this.plantedGrowable != null && !this.plantedGrowable.isAlive()) {
            this.plantedGrowable = null;
            this.cultivated = false;
        }
    }

    @Override
    public int getGrowablePopulation() {
        if (this.isCultivated()) {
            return this.plantedGrowable.getPopulation();
        }

        return MIN_CULTIAVED_POPULATION;
    }

    public boolean isCultivated() {
        return this.cultivated;
    }
}
