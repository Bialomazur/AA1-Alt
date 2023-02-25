package model.map;

import model.growable.Growable;

public abstract class NewTile {
    private int x;
    private int y;
    private TileType tileType = TileType.EMPTY;


    protected NewTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setTileType(TileType type) {
        this.tileType = type;
    }

    public abstract void putGrowable(Growable growable);

    public abstract Growable takeGrowable(Growable growable, int quantity);

    public abstract int getGrowablePopulation();

    public boolean plantTypeAllowed(Growable growable) {
        return this.tileType.getAllowedPlants().contains(growable.getPlantType());
    }


}
