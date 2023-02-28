package model.map;

import model.growable.Growable;
import model.time.Updateable;

public abstract class Tile implements Updateable {
    protected static final int INITIAL_ROUNDS_PASSED = 0;
    protected static final int ROUNDS_PASSED_INCREMENT = 1;
    private int xCoordinate;
    private int yCoordinate;
    private TileCoordinate tileCoordinate;
    private final Biotope biotope;
    private int roundsPassedUntilUpdateAction = INITIAL_ROUNDS_PASSED;

    protected Tile(final int xCoordinate, final int yCoordinate, Biotope biotope) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.biotope = biotope;
    }

    public int getxCoordinate() {
        return this.xCoordinate;
    }

    public int getyCoordinate() {
        return this.yCoordinate;
    }

    public void setxCoordinate(final int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(final int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public abstract void putGrowable(final Growable growable);

    public abstract Growable takeGrowable(final Growable growable, final int quantity);

    public abstract int getGrowablePopulation();

    public int getRoundsPassedUntilUpdateAction() {
        return this.roundsPassedUntilUpdateAction;
    }

    public Biotope getBiotope() {
        return this.biotope;
    }

    public void setRoundsPassedUntilUpdateAction(final int roundsPassedUntilUpdateAction) {
        this.roundsPassedUntilUpdateAction = roundsPassedUntilUpdateAction;
    }

    public abstract int  getUpdatesLeftUntilAction();

    public abstract boolean isEmpty();
    public abstract String toString();
}
