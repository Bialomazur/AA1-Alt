package command.setup;

import command.GameCommand;
import model.Game;

import java.util.List;

public class SetStartingGoldCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 1;
    private static final int MAX_ARGUMENT_COUNT = 1;
    private static final int GOLD_INDEX = 0;
    private static final int MIN_STARTING_GOLD = 0;

    private static final String STARTING_GOLD_MUST_AT_LEAST_BE = "Starting gold must at least be %d.";
    private static final String STARTING_GOLD_MUST_BE_AN_INTEGER = "Starting gold must be an integer.";


    public SetStartingGoldCommand(final Game game) {
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
        int gold = 0;
        try {
            gold = Integer.parseInt(args.get(GOLD_INDEX));
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(STARTING_GOLD_MUST_BE_AN_INTEGER);
        }

        if (gold < MIN_STARTING_GOLD) {
            throw new IllegalArgumentException(STARTING_GOLD_MUST_AT_LEAST_BE.formatted(MIN_STARTING_GOLD));
        }
    }

    @Override
    public void execute() {
        this.getGame().setStartingGold(Integer.parseInt(this.getArgs().get(GOLD_INDEX)));
    }
}
