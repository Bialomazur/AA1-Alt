package command.action;

import command.GameCommand;
import model.Game;

import java.util.List;

public class PlantGrowableCommand extends GameCommand {

    public PlantGrowableCommand(Game game) {
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
    protected void validateArgumentsContent(List<String> args) {

    }

    @Override
    public void execute() {

    }
}
