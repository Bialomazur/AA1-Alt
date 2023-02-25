package command.setup;

import command.GameCommand;
import model.Game;

import java.util.List;

public class DispatchTileCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 4;
    private static final int MAX_ARGUMENT_COUNT = 4;

    private static final int PLAYER_ID_INDEX = 0;
    private static final int sBIOTOPE_TYPE_INDEX = 1;
    private static final int X_COORDINATE_INDEX = 2;
    private static final int Y_COORDINATE_INDEX = 3;

    private static final String INVALID_PLAYER_ID = "Invalid player ID.";
    private static final String INVALID_BIOTOPE_TYPE = "Invalid biotope type.";
    private static final String INVALID_X_COORDINATE = "Invalid x-coordinate.";
    private static final String INVALID_Y_COORDINATE = "Invalid y-coordinate.";

    protected DispatchTileCommand(Game game) {
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
        try {
            final int playerId = Integer.parseInt(args.get(PLAYER_ID_INDEX));
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_PLAYER_ID);
        }
    }

    @Override
    public void execute() {

    }
}
