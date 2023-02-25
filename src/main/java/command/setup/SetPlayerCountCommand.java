package command.setup;

import command.GameCommand;
import model.Game;

import java.util.List;

public class SetPlayerCountCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 1;
    private static final int MAX_ARGUMENT_COUNT = 1;

    private static final int MIN_PLAYER_COUNT = 1;
    private static final int PLAYER_COUNT_ARG_INDEX = 0;

    private static final String INVALID_PLAYER_COUNT = "Player count must be an integer.";
    private static final String AMOUNT_OF_PLAYERS_MUST_AT_LEAST_BE = "The amount of players must at lest be %d.";

    public SetPlayerCountCommand(final Game game) {
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
        final int playerCount;
        try {
            playerCount = Integer.parseInt(args.get(PLAYER_COUNT_ARG_INDEX));
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_PLAYER_COUNT);
        }

        if (playerCount < MIN_PLAYER_COUNT) {
            throw new IllegalArgumentException(String.format(AMOUNT_OF_PLAYERS_MUST_AT_LEAST_BE, MIN_PLAYER_COUNT));
        }
    }

    @Override
    public void execute() {
        this.getGame().setPlayerCount(Integer.parseInt(this.getArgs().get(PLAYER_COUNT_ARG_INDEX)));
    }
}
