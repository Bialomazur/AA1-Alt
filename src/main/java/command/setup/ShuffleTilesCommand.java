package command.setup;

import command.GameCommand;
import model.Game;

import java.util.List;

public class ShuffleTilesCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 1;
    private static final int MAX_ARGUMENT_COUNT = 1;
    private static final int SEED_INDEX = 0;
    public ShuffleTilesCommand(final Game game) {
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


    /**
     *
     * No explicit seed-range check is needed, as it states in the specification that
     * the range is between {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE.
     *
     * */
    @Override
    protected void validateArgumentsContent(final List<String> args) {
        final int seed = Integer.parseInt(args.get(SEED_INDEX));
    }

    @Override
    public void execute() {

    }
}
