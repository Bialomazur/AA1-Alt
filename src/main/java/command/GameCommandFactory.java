package command;

import model.Game;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GameCommandFactory {
    private final Game game;
    private final Map<String, GameCommand> registeredCommands = new HashMap<>();

    public GameCommand buildCommand(final String command, final List<String> args) {
        GameCommand gameCommand = new EmptyGameCommand(this.game);

        for (final Entry<String, GameCommand> entry : this.registeredCommands.entrySet()) {
            if (entry.getKey().equals(command)) {
                gameCommand = entry.getValue();
                gameCommand.setArgs(args);
            }
        }

        return gameCommand;
    }

    public GameCommandFactory(final Game game) {
        this.game = game;
    }

    public void register(final String command, final GameCommand gameCommand) {
        this.registeredCommands.put(command, gameCommand);
    }

    public void unregister(final String command) {
        this.registeredCommands.remove(command);
    }


    public boolean isRegistered(final String command) {
        return this.registeredCommands.containsKey(command);
    }
}
