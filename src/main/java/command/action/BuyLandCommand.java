package command.action;

import command.GameCommand;
import model.Game;
import model.Player;

import java.util.List;

public class BuyLandCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 2;
    private static final int MAX_ARGUMENT_COUNT = 2;
    private static final int X_COORDINATE_INDEX = 0;
    private static final int Y_COORDINATE_INDEX = 1;

    private static final String COORDINATES_MUST_BE_INTEGERS = "Coordinates must be integers";

    public BuyLandCommand(final Game game) {
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
    protected void validateArgumentsContent(List<String> args) {
        boolean validCorrdinates = true;
        for (final String coordinate : this.getArguments()) {
            try {
                Integer.parseInt(coordinate);
            } catch (final NumberFormatException e) {
                validCorrdinates = false;
            }
        }

        if (!validCorrdinates) {
            throw new IllegalArgumentException(COORDINATES_MUST_BE_INTEGERS);
        }
    }

    @Override
    public void execute() {
        final int xCoordinate = Integer.parseInt(this.getArguments().get(X_COORDINATE_INDEX));
        final int yCoordinate = Integer.parseInt(this.getArguments().get(Y_COORDINATE_INDEX));
        final Player currentPlayer = this.getGame().getCurrentPlayer();

        //TODO: Figure out how to fulfill the transaction
    }
}
