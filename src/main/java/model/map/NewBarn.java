package model.map;

import model.growable.Growable;

import java.util.HashSet;
import java.util.Set;

public class NewBarn extends NewTile{
    private final Set<Growable> storage = new HashSet<>();
    private static final int MIN_GROWABLE_POPULATION_TO_STORE = 1;
    private static final String TOO_FEW_UNITS_TO_STORE = "Cannot store a vegetable with a population of less than %d.";


    protected NewBarn(final int x, final int y) {
        super(x, y);
        this.setTileType(TileType.BARN);
    }

    @Override
    public void putGrowable(final Growable growable) {
        if (growable.getPopulation() < MIN_GROWABLE_POPULATION_TO_STORE) {
            throw new IllegalArgumentException(TOO_FEW_UNITS_TO_STORE.formatted(MIN_GROWABLE_POPULATION_TO_STORE));
        }

        for (final Growable storedGrowable : this.storage) {
            if (storedGrowable.getPlantType() == growable.getPlantType()) {
                storedGrowable.setPopulation(storedGrowable.getPopulation() + growable.getPopulation());
            }
        }

    }

    @Override
    public Growable takeGrowable(final Growable growable, final int quantity) {
        return null;
    }

    @Override
    public int getGrowablePopulation() {
        return 0;
    }
}
