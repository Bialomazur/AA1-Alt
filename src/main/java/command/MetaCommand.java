package command;

import model.game.Game;

import java.util.List;

//TODO: Figure out how to realize meta commands
public class MetaCommand extends GameCommand {
    public MetaCommand(final Game game) {
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
