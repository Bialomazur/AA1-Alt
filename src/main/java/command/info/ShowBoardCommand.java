package command.info;

import command.GameCommand;
import model.game.Game;
import model.map.Biotope;
import model.map.CultivableTile;
import model.map.Tile;
import model.map.TileMap;

import java.util.List;

public class ShowBoardCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 0;
    private static final int MAX_ARGUMENT_COUNT = 0;

    private static final int CONTENT_WIDTH = 5;
    private static final int SEPARATED_WIDTH = CONTENT_WIDTH + 2;
    private static final int LINE_HEIGHT = 3;

    private static final String EMPTY_SPACE = " ";
    private static final String TILE_SEPARATOR = "|";

    public ShowBoardCommand(final Game game) {
        super(game);
    }

    @Override
    protected int getMinArgumentCount() {
        return MIN_ARGUMENT_COUNT;
    }

    @Override
    protected int getMaxArgumentCount() {
        return MAX_ARGUMENT_COUNT;
    }


    @Override
    protected void validateArgumentsContent(final List<String> args) {

    }

    private int getMaxXCoordinate(final TileMap tileMap) {
        int horizontalTileCount = 0;
        for (final Tile tile : tileMap.getTiles()) {
            horizontalTileCount = Math.max(horizontalTileCount, tile.getxCoordinate());
        }
        return horizontalTileCount;

    }

    private int getMinXCoordinate(final TileMap tileMap) {
        int horizontalTileCount = 0;
        for (final Tile tile : tileMap.getTiles()) {
            horizontalTileCount = Math.min(horizontalTileCount, tile.getxCoordinate());
        }
        return horizontalTileCount;
    }

    private int getMaxYCoordinate(final TileMap tileMap) {
        int verticalTileCount = 0;
        for (final Tile tile : tileMap.getTiles()) {
            verticalTileCount = Math.max(verticalTileCount, tile.getyCoordinate());
        }
        return verticalTileCount;
    }


    public static Tile[][] transposeMatrix(final Tile[][] m) {
        final Tile[][] temp = new Tile[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    private Tile[][] buildBoard(final TileMap tileMap) {
        final List<Tile> tilesToDisplay = tileMap.getTiles();

        final int horizontalMax = this.getMaxXCoordinate(tileMap);
        final int horizontalMin = this.getMinXCoordinate(tileMap);
        final int verticalMax = this.getMaxYCoordinate(tileMap);

        final int xOffset = horizontalMin >= 0 ? 0 : Math.abs(horizontalMin);

        final Tile[][] board = new Tile[horizontalMax + xOffset + 1][verticalMax + 1];
        for (final Tile tile : tilesToDisplay) {
            board[tile.getxCoordinate() + xOffset][tile.getyCoordinate()] = tile;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    board[i][j] = new CultivableTile(Biotope.EMPTY, i, j);
                }
            }
        }

        return transposeMatrix(board);
    }

    private String createEmptyTileEntry(final String[][] transposed, final int startY, final int startX) {
        final StringBuilder emptyTileEntry = new StringBuilder();
        for (int y = startY; y < LINE_HEIGHT; y++) {
            for (int x = startX; x < CONTENT_WIDTH; x++) {
                transposed[y][x] = "_";
            }
            emptyTileEntry.append("\n");
        }
        return emptyTileEntry.toString();
    }

    private String buildEntry(final Tile tile) {
        final StringBuilder entry = new StringBuilder();

        return "";
    }

    private String buildTileString(final Tile tile) {
        final StringBuilder tileString = new StringBuilder();
        return tileString.toString();
    }

    private void insertSeperatorColumn(final String[][] boardString, final int x) {
        for (int y = 0; y < boardString.length; y++) {
            boardString[y][x] = TILE_SEPARATOR;
        }
    }

    private String createCultivableTileEntry(final String[][] boardString, final int startY, final int startX, final Tile tile) {
        final StringBuilder filledTileEntry = new StringBuilder();
        final String[] tileStringLines = tile.toString().split("\n");

        for (int y = startY * LINE_HEIGHT; y < LINE_HEIGHT * (startY + 1); y++) {
            insertSeperatorColumn(boardString, startX * SEPARATED_WIDTH);
            for (int x = startX * CONTENT_WIDTH; x < CONTENT_WIDTH * (startX + 1); x++) {
                boardString[y][x + 1] = tileStringLines[y - startY * LINE_HEIGHT].charAt(x - startX * CONTENT_WIDTH)+"";

               // else
            }
        }

        return filledTileEntry.toString();
    }

    @Override
    public void execute() {
        final TileMap tileMap = this.getGame().getCurrentPlayer().getTileMap();
        final StringBuilder boardString = new StringBuilder();

        final Tile[][] transposed = this.buildBoard(tileMap);
        final String[][] output = new String[transposed.length * LINE_HEIGHT][transposed[0].length * SEPARATED_WIDTH ];

        for (int i = transposed.length - 1; i >= 0; i--) {
            for (int j = 0; j < transposed[i].length; j++) {
                boardString.append(transposed[i][j].getBiotope().getNickName());
                boardString.append(" ");
            }
            if (i != 0) {
                boardString.append("\n");
            }
        }

        for (int i = 0; i < transposed.length; i++) {
            for (int j = 0; j < transposed[i].length; j++) {
                this.createCultivableTileEntry(output, i, j, transposed[i][j]);
            }
        }
        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < output.length; i++) {
            String row = "";
            for (int j = 0; j < output[i].length; j++) {
                final String toAppend = output[i][j];
                result.append(toAppend);
                row += output[i][j];
            }
            row = "";
            result.append("\n");
        }

        this.setOutput(boardString.toString());
    }
}
