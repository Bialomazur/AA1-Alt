package model.map;

import model.Player;

import java.util.LinkedList;
import java.util.Queue;

public class TileDispatcher {

    private final Queue<Tile> tiles = new LinkedList<>();
    private static final String TILE_ALREADY_IN_POSSESSION = "Tile already in possession";
    private static final String NOT_ENOUGH_GOLD_TO_BUY_TILE = "The selected tile costs %d gold but you only have %d";
    private static final String NO_TILES_LEFT_TO_BUY = "No tiles left to buy";

    public void dispatchTile(final Player player) {
      //  player.getTileMap().addTile(this.tiles.poll());
    }

    private int calculateTileCost(final TileMap tileMap, final int x, final int y) {
        return 0;
    }

    public void buyCultivableTile(final Player player, final int x, final int y) {
        final TileMap tileMap = player.getTileMap();

        if (tileMap.hasTileAt(x, y)) {
            throw new IllegalArgumentException(TILE_ALREADY_IN_POSSESSION);
        }

        if (this.tiles.isEmpty()) {
            throw new IllegalStateException(NO_TILES_LEFT_TO_BUY);
        }

        final int tileCost = this.calculateTileCost(tileMap, x, y);

        if (player.getGold() < tileCost) {
            throw new IllegalArgumentException(String.format(NOT_ENOUGH_GOLD_TO_BUY_TILE, tileCost, player.getGold()));
        }

        player.setGold(player.getGold() - tileCost);
        this.dispatchTile(player);
    }


}
