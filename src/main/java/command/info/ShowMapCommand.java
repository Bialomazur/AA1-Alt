package command.info;

import command.GameCommand;
import model.Game;

import java.util.List;

public class ShowMapCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 0;
    private static final int MAX_ARGUMENT_COUNT = 0;

    public ShowMapCommand(Game game) {
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

    }
}
