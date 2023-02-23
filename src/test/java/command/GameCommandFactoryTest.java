package command;

import io.CommandWord;
import model.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameCommandFactoryTest {
    private static final int WINNING_GOLD_AMOUNT = 500;


    @Test
    void reflectionTest() throws NoSuchMethodException {
        AddPlayerCommand.class.getConstructor(Game.class, List.class);

    }
    @Test
    void basicTest() {
        Game game = new Game(100, WINNING_GOLD_AMOUNT);
        GameCommandFactory factory = new GameCommandFactory(game);
        factory.registerCommand(CommandWord.ADD_PLAYER.getWord(), command.AddPlayerCommand.class);
        GameCommand command = factory.buildCommand(CommandWord.ADD_PLAYER.getWord(), List.of("test"));
        assertTrue(command instanceof command.AddPlayerCommand);

    }

}