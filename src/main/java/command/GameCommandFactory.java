package command;

import model.Game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GameCommandFactory {
    private final Game game;
    private final Map<String, Class<? extends GameCommand>> commandRegistry = new HashMap<>();


    //TODO: Review this method due to dry-violation in return command and inappropriate exception handling
    public GameCommand buildCommand(String commandString, List<String> args) {
        GameCommand command = new EmptyGameCommand(this.game, args);
        try {
            for (Entry<String, Class<? extends GameCommand>> commandEntry : this.commandRegistry.entrySet()) {
                if (commandString.equals(commandEntry.getKey())) {
                    Constructor<? extends GameCommand> constructor = commandEntry.getValue().getConstructor(Game.class, List.class);
                    command = constructor.newInstance(this.game, args);
                }
            }

            return command;

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

            return command;
        }
    }

    public GameCommandFactory(Game game) {
        this.game = game;
    }

    public void registerCommand(String commandName, Class<? extends GameCommand> commandClass) {
        this.commandRegistry.put(commandName, commandClass);
    }
}
