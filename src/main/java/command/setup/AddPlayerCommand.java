package command.setup;

import command.GameCommand;
import model.Game;

import java.util.List;

public class AddPlayerCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 1;
    private static final int MAX_ARGUMENT_COUNT = 1;
    private static final String PLAYER_NAME_REGEX = "[a-zA-Z]+";
    private static final int PLAYER_NAME_ARG_INDEX = 0;
    private static final String CANNOT_ADD_PLAYER_TO_RUNNING_GAME = "Cannot add a player to a running game.";
    private static final String INVALID_PLAYER_NAME = "Player name contains illegal characters.";
    private static final String PLAYER_NAME_BLANK = "Player name cannot be blank.";

    public AddPlayerCommand(final Game game) {
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
        if (args.get(PLAYER_NAME_ARG_INDEX).isBlank()) {
            throw new IllegalArgumentException(PLAYER_NAME_BLANK);
        }

        if (!args.get(PLAYER_NAME_ARG_INDEX).matches(PLAYER_NAME_REGEX)) {
            throw new IllegalArgumentException(INVALID_PLAYER_NAME);
        }
    }

    @Override
    public void execute() {
        if (this.getGame().isRunning()) {
            throw new IllegalStateException(CANNOT_ADD_PLAYER_TO_RUNNING_GAME);
        }

        this.getGame().addPlayer(this.getArgs().get(PLAYER_NAME_ARG_INDEX));
    }
}
