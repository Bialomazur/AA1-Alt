package model.map;

import model.growable.Growable;

import java.util.HashSet;
import java.util.Set;

public class NewBarn extends NewTile{
    private final Set<Growable> storage = new HashSet<>();
    private static final String GROWABLE_NOT_IN_STORE = "Cannot take a growable that is not stored.";
    private static final String NOT_ENOUGH_UNITS_STORED = "Not enough %s stored.";

    protected NewBarn(final int x, final int y) {
        super(x, y);
        this.setTileType(TileType.BARN);
    }

    private Growable getStoredGrowable(final Growable growable) {
        for (final Growable barnGrowable : this.storage) {
            if (barnGrowable.equals(growable)) {
                return barnGrowable;
            }
        }
        throw new IllegalArgumentException(GROWABLE_NOT_IN_STORE);
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
}
