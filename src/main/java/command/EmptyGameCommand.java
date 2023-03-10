package command;

import model.game.Game;

import java.util.List;

/**
 * Class modelling an empty game command.
 * The empty game command is used for representing an "invalid" command doing nothing
 * and thus preventing unnecessary null-checks.
 *
 * @author uejxk
 * @version 1.0
 * */

public class EmptyGameCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 0;
    private static final int MAX_ARGUMENT_COUNT = 0;
    public EmptyGameCommand(final Game game) {
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

    @Override
    public void execute() {

    }
}
