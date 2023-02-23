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

    public GameCommand getGameCommand(String command, List<String> args) {
        GameCommand gameCommand = new EmptyGameCommand(this.game);

        for (Entry<String, GameCommand> entry : registeredCommands.entrySet()) {
            if (entry.getKey().equals(command)) {
                gameCommand = entry.getValue();
            }
        }

        gameCommand.setArgs(args);
        return gameCommand;
    }

    public GameCommandFactory(Game game) {
        this.game = game;
    }

    public void registerCommand(String command, GameCommand gameCommand) {
        this.registeredCommands.put(command, gameCommand);
    }

    public Map<String, GameCommand> getRegisteredCommands() {
        return Collections.unmodifiableMap(this.registeredCommands);
    }
}
