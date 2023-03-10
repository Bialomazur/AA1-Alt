package command.info;

import command.GameCommand;
import model.game.Game;

import java.util.List;

public class ShowMarketCommand extends GameCommand {

    private static final int MIN_ARGUMENT_COUNT = 0;
    private static final int MAX_ARGUMENT_COUNT = 0;

    public ShowMarketCommand(final Game game) {
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
