package command;

import model.Game;

import java.util.List;

public class AddPlayerCommand extends GameCommand{
    private static final int MIN_ARGUMENT_COUNT = 1;
    private static final int MAX_ARGUMENT_COUNT = 1;
    private static final String PLAYER_NAME_REGEX = "[a-zA-Z]+"; //TODO: Add regex for player name
    private static final int PLAYER_NAME_ARG_INDEX = 0;
    private static final String CANNOT_ADD_PLAYER_TO_RUNNING_GAME = "Cannot add a player to a running game.";

    public AddPlayerCommand(Game game, List<String> args) {
        super(game, args);
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
    protected boolean verifyArgumentsContent() {
        return this.getArguments().get(PLAYER_NAME_ARG_INDEX).matches(PLAYER_NAME_REGEX);
    }

    @Override
    public void execute() {
        if (this.getGame().isRunning()) {
            throw new IllegalStateException(CANNOT_ADD_PLAYER_TO_RUNNING_GAME);
        }

        this.getGame().addPlayer(this.getArguments().get(PLAYER_NAME_ARG_INDEX));
    }
}
