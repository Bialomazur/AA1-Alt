package model.map;

import model.game.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class TileDispatcher {
    private final Queue<Tile> tiles = new LinkedList<>();
    private static final String TILE_ALREADY_IN_POSSESSION = "Tile already in possession";
    private static final String NOT_ENOUGH_GOLD_TO_BUY_TILE = "The selected tile costs %d gold but you only have %d";
    private static final String NO_TILES_LEFT_TO_BUY = "No tiles left to buy";

    private final int lobbySize;

    private static final int GARDENS_PER_PLAYER = 2;
    private static final int FIELDS_PER_PLAYER = 3;
    private static final int LARGE_FIELDS_PER_PLAYER = 2;
    private static final int FORESTS_PER_PLAYER = 2;
    private static final int LARGE_FORESTS_PER_PLAYER = 1;

    private static final int DEFAULT_X_COORDINATE = 0;
    private static final int DEFAULT_Y_COORDINATE = 0;

    private final Map<Biotope, Integer> dispatchableBiotopeQuantities = Map.of(
            Biotope.GARDEN, GARDENS_PER_PLAYER,
            Biotope.FIELD, FIELDS_PER_PLAYER,
            Biotope.LARGE_FIELD, LARGE_FIELDS_PER_PLAYER,
            Biotope.FOREST, FORESTS_PER_PLAYER,
            Biotope.LARGE_FOREST, LARGE_FORESTS_PER_PLAYER
    );

    private final List<Biotope> orderedDispatchableBiotopes = List.of(
            Biotope.GARDEN,
            Biotope.FIELD,
            Biotope.LARGE_FIELD,
            Biotope.FOREST,
            Biotope.LARGE_FOREST
    );

    public TileDispatcher(final int lobbySize) {
        this.lobbySize = lobbySize;
    }

    private void dispatchTile(final TileMap tileMap, final int xCoordinate, final int yCoordinate) {
        if (this.tiles.isEmpty()) {
            throw new IllegalStateException(NO_TILES_LEFT_TO_BUY);
        }
        Tile tileToDispatch = this.tiles.poll();
        tileToDispatch.setxCoordinate(xCoordinate);
        tileToDispatch.setyCoordinate(yCoordinate);
        tileMap.addTile(tileToDispatch);
    }


    public void shuffleTiles(final int seed) {
        final Random random = new Random(seed);
        final List<Tile> listOfTilesToShuffle = new LinkedList<>(this.tiles);
        Collections.shuffle(listOfTilesToShuffle, random);
        this.tiles.clear();
        this.tiles.addAll(listOfTilesToShuffle);
    }

    private int calculateTileCost(final TileMap tileMap, final int xCoordinate, final int y) {
        return 0;
    }

    //DEBUG: Implement check whether tile is reachable
    public void buyCultivableTile(final Player player, final int xCoordinate, final int yCoordinate) {
        final TileMap tileMap = player.getTileMap();

        if (tileMap.hasTileAt(xCoordinate, yCoordinate)) {
            throw new IllegalArgumentException(TILE_ALREADY_IN_POSSESSION);
        }

        final int tileCost = this.calculateTileCost(tileMap, xCoordinate, yCoordinate);

        if (player.getGold() < tileCost) {
            throw new IllegalArgumentException(String.format(NOT_ENOUGH_GOLD_TO_BUY_TILE, tileCost, player.getGold()));
        }

        this.dispatchTile(tileMap, xCoordinate, yCoordinate);
        player.setGold(player.getGold() - tileCost);
    }

    public List<Tile> getDispatchableTiles() {
        return new LinkedList<>(this.tiles);
    }

    public void init() {
        for (final Biotope biotope : this.orderedDispatchableBiotopes) {
            final int totalQuantity = this.dispatchableBiotopeQuantities.get(biotope) * this.lobbySize;
            for (int i = 0; i < totalQuantity; i++) {
                this.tiles.add(new CultivableTile(biotope, DEFAULT_X_COORDINATE, DEFAULT_Y_COORDINATE));
            }
        }
    }


}
