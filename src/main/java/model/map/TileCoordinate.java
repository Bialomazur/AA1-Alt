package model.map;

import java.util.Objects;

public class TileCoordinate {
    private final int xCoordinate;
    private final int yCoordinate;

    public TileCoordinate(final int xCoordinate, final int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        final TileCoordinate that = (TileCoordinate) other;
        return this.xCoordinate == that.xCoordinate && this.yCoordinate == that.yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.xCoordinate, this.yCoordinate);
    }

    public int getxCoordinate() {
        return this.xCoordinate;
    }

    public int getyCoordinate() {
        return this.yCoordinate;
    }
}
