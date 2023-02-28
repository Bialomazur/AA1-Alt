package model.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TileMap {
    private static final int X_COORDINATE_INDEX = 0;
    private static final int Y_COORDINATE_INDEX = 1;
    private static final int[] BARN_COORDINATES = new int[] {0, 0};
    private final Barn barn = new Barn(BARN_COORDINATES[X_COORDINATE_INDEX], BARN_COORDINATES[Y_COORDINATE_INDEX]);
    private final List<Tile> tiles = new ArrayList<>();

    //DEBUG: make sure to explain why this does NOT pose a magic number violation!
    private final Map<int[], Biotope> initialCultivableTiles = Map.of(
            new int[] {-1, 0}, Biotope.GARDEN,
            new int[] {1, 0}, Biotope.GARDEN,
            new int[] {0, 1}, Biotope.FIELD
    );

    private static final String MAP_HAS_NO_TILE_AT = "Map has no tile at (%d, %d).";


    public void addTile(final Tile tile) {
        this.tiles.add(tile);
    }

    public void addAllTiles(final Set<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    public Tile getTileAt(final int xCoordinate, final int yCoordinate) {
        for (final Tile tile : this.tiles) {
            if (tile.getxCoordinate() == xCoordinate && tile.getyCoordinate() == yCoordinate) {
                return tile;
            }
        }
        throw new IllegalArgumentException(String.format(MAP_HAS_NO_TILE_AT, xCoordinate, yCoordinate));
    }

    public boolean hasTileAt(final int xCoordinate, final int yCoordinate) {
        for (final Tile tile : this.tiles) {
            if (tile.getxCoordinate() == xCoordinate && tile.getyCoordinate() == yCoordinate) {
                return true;
            }
        }
        return false;
    }

    // TODO: Make sure to clarify that a shallow copy is needed here!

    public Barn getBarn() {
        return this.barn;
    }

    public List<Tile> getTiles() {
        return new ArrayList<>(this.tiles);
    }
    public void init() {
        for (final Entry<int[], Biotope> entry : this.initialCultivableTiles.entrySet()) {
            final int xCoordinate = entry.getKey()[X_COORDINATE_INDEX];
            final int yCoordinate = entry.getKey()[Y_COORDINATE_INDEX];
            final Biotope biotope = entry.getValue();
            this.tiles.add(new CultivableTile(biotope, xCoordinate, yCoordinate));
        }

        this.tiles.add(this.barn);
    }


}
