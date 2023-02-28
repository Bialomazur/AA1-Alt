package command.setup;

import command.GameCommand;
import model.game.Game;

import java.util.List;

public class SetTileSeedCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 1;
    private static final int MAX_ARGUMENT_COUNT = 1;
    private static final int SEED_INDEX = 0;


    //DEBUG: MAKE SURE THAT SEED DOES ACTUALLY HAVE TO BE AN INTEGER AND NOT A FLOAT!
    private static final String SEED_MUST_BE_AN_INTEGER = "The seed must be an integer";

    public SetTileSeedCommand(final Game game) {
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
     * */
    @Override
    protected void validateArgumentsContent(final List<String> args) {
        try {
            Integer.parseInt(args.get(SEED_INDEX));
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(SEED_MUST_BE_AN_INTEGER);
        }
    }

    @Override
    public void execute() {
        this.getGame().setShuffleSeed(Integer.parseInt(this.getArgs().get(SEED_INDEX)));
    }
}
