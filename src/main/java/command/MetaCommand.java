package command;

import model.Game;

import java.util.List;

public class MetaCommand extends GameCommand {
    MetaCommand(Game game, List<String> args) {
        super(game, args);
    }

    @Override
    protected int getMinArgumentCount() {
        return 0;
    }

    @Override
    protected int getMaxArgumentCount() {
        return 0;
    }

    @Override
    protected boolean verifyArgumentsContent() {
        return false;
    }

    @Override
    public void execute() {

    }
}
