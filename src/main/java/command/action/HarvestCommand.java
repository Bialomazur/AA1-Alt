package command.action;

import command.GameCommand;
import model.game.Game;

import java.util.List;

public class HarvestCommand extends GameCommand {
    public HarvestCommand(final Game game) {
        super(game);
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
    protected void validateArgumentsContent(final List<String> args) {

    }
    @Override
    public void execute() {

    }
}
