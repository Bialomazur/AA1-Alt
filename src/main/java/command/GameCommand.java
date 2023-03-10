package command;

import model.game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GameCommand {
    private final Game game;
    private final List<String> args = new ArrayList<>();
    private String output = Output.EMPTY_OUTPUT.format();
    private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments";
    protected GameCommand(final Game game) {
        this.game = game;
    }

    protected abstract int getMinArgumentCount();

    protected abstract int getMaxArgumentCount();

    protected List<String> getArgs() {
        return Collections.unmodifiableList(this.args); //TODO: Check if package java.util.Collections is allowed
    }

    protected Game getGame() {
        return this.game;
    }

    protected void setOutput(final String output) {
        this.output = output;
    }

    public String flush() {
        final String result = this.output;
        this.output = Output.EMPTY_OUTPUT.format();
        return result;
    }

    public void setArgs(final List<String> args) {
        if (args.size() < this.getMinArgumentCount() || args.size() > this.getMaxArgumentCount()) {
            throw new IllegalArgumentException(INVALID_NUMBER_OF_ARGUMENTS);
        }

        //TODO: fishy because the commands validate arguments themselves and throw according exceptions
        this.validateArgumentsContent(args);
        this.args.clear();
        this.args.addAll(args);
    }

    protected abstract void validateArgumentsContent(List<String> args);

    public abstract void execute();
}
