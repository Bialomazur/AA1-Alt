package command.flow;

import command.GameCommand;
import model.Game;

import java.util.List;


/**
 * Class modelling a command that ends the current player's turn.
 *
 * @author uejxk
 * @version 1.0
 * */

public class EndTurnCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 0;
    private static final int MAX_ARGUMENT_COUNT = 0;
    private static final boolean NO_ARGUMENT_CONTENT_VERIFICATION = true;
    private static final String GAME_NOT_RUNNING = "The game is not running.";

    public EndTurnCommand(final Game game) {
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

    }

    @Override
    public void execute() {
        if (!this.getGame().isRunning()) {
            throw new IllegalStateException(GAME_NOT_RUNNING);
        }

        this.getGame().endTurn();
    }
}
