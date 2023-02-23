package command;

import io.CommandWord;
import model.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameCommandFactoryTest {
    private static final int WINNING_GOLD_AMOUNT = 500;


    @Test
    void basic() {
        Game game = new Game(2,WINNING_GOLD_AMOUNT);
        GameCommand command = new AddPlayerCommand(game);
        System.out.println(command.getClass());
    }


}