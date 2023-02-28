package command;

import command.info.ShowBarnCommand;
import model.game.Game;
import model.growable.Growable;
import model.growable.PlantType;
import model.map.Barn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ShowBarnCommandTest {

    private static final List<String> ARGS = new ArrayList<>();
    private static Game game;

    @BeforeEach
    void init() {
        game = new Game();
        game.createLobby(1);
    }


    private void log(final String message) {
        System.out.println(message);
    }

    @Test
    void goldAmountBiggerThanTotalGrowableCount() {
        game.setStartingGold(5000);
        game.setWinningGold(100000000);
        game.addPlayer("Player 1");

        final GameCommand command = new ShowBarnCommand(game);
        command.setArgs(ARGS);
        game.start();

        for (final PlantType plantType : PlantType.values()) {
            game.getPlayerById(1).getTileMap().getBarn().putGrowable(new Growable(plantType, 99));
        }

        final String correctResult = "Barn (spoils in 6 turns)\n" +
                "carrots:    100\n" +
                "mushrooms:  100\n" +
                "salads:     100\n" +
                "tomatoes:   100\n" +
                "---------------\n" +
                "Sum:        400\n" +
                "\n" +
                "Gold:      5000";

        command.execute();
        final String result = command.flush();
        assertEquals(correctResult, result);
    }

    @Test
    void goldAmountSmallerThanTotalGrowableCount() {
        game.setStartingGold(5);
        game.setWinningGold(100000000);
        game.addPlayer("Player 1");

        final GameCommand command = new ShowBarnCommand(game);
        command.setArgs(ARGS);
        game.start();

        for (final PlantType plantType : PlantType.values()) {
            game.getPlayerById(1).getTileMap().getBarn().putGrowable(new Growable(plantType, 249));
        }

        final String correctResult = "Barn (spoils in 6 turns)\n" +
                "carrots:    250\n" +
                "mushrooms:  250\n" +
                "salads:     250\n" +
                "tomatoes:   250\n" +
                "---------------\n" +
                "Sum:       1000\n" +
                "\n" +
                "Gold:         5";

        command.execute();
        final String result = command.flush();
        assertEquals(correctResult, result);
    }


    @Test
    void barnWithDifferingGrowableCounts() {
        game.setStartingGold(5);
        game.setWinningGold(100000000);
        game.addPlayer("Player 1");

        final GameCommand command = new ShowBarnCommand(game);
        command.setArgs(ARGS);
        game.start();

        final Growable carrot = new Growable(PlantType.CARROT, 99);
        final Growable mushroom = new Growable(PlantType.MUSHROOM, 99);
        final Growable salad = new Growable(PlantType.SALAD, 49);
        final Growable tomato = new Growable(PlantType.TOMATO, 49);

        game.getCurrentPlayer().getTileMap().getBarn().putGrowable(carrot);
        game.getCurrentPlayer().getTileMap().getBarn().putGrowable(mushroom);
        game.getCurrentPlayer().getTileMap().getBarn().putGrowable(salad);
        game.getCurrentPlayer().getTileMap().getBarn().putGrowable(tomato);

        final String correctResult = "Barn (spoils in 6 turns)\n" +
                "carrots:   100\n" +
                "mushrooms: 100\n" +
                "salads:     50\n" +
                "tomatoes:   50\n" +
                "--------------\n" +
                "Sum:       300\n\n" +
                "Gold:        5";

        command.execute();
        final String result = command.flush();
        assertEquals(correctResult, result);
    }


    @Test
    void barnWithoutSomeGrowablesAndNotEmpty() {
        game.setStartingGold(5);
        game.setWinningGold(100000000);
        game.addPlayer("Player 1");

        final GameCommand command = new ShowBarnCommand(game);
        command.setArgs(ARGS);
        game.start();
        final Barn barn = game.getCurrentPlayer().getTileMap().getBarn();
        barn.spoil();

        final Growable carrot = new Growable(PlantType.CARROT, 50);
        final Growable mushroom = new Growable(PlantType.MUSHROOM, 100);

        barn.putGrowable(carrot);
        barn.putGrowable(mushroom);

        final String correctResult = "Barn (spoils in 6 turns)\n" +
                "mushrooms: 100\n" +
                "carrots:    50\n" +
                "--------------\n" +
                "Sum:       150\n\n" +
                "Gold:        5";

        command.execute();
        final String result = command.flush();
        assertEquals(correctResult, result);
    }

    @Test
    void emptyBarn() {
        game.setStartingGold(5);
        game.setWinningGold(100000000);
        game.addPlayer("Player 1");

        final GameCommand command = new ShowBarnCommand(game);
        command.setArgs(ARGS);
        game.start();
        game.getCurrentPlayer().getTileMap().getBarn().spoil();

        final String correctResult = "Barn\n" +
                "Gold: 5";

        command.execute();
        final String result = command.flush();
        assertEquals(correctResult, result);
    }
}