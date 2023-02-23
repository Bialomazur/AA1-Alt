package command;

import model.Game;
import model.growable.Growable;
import model.growable.PlantType;
import model.map.Barn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ShowBarnCommandTest {

    private static final List<String> ARGS = new ArrayList<>();


    @BeforeAll
    static void init() {
    }


    private void log(String message) {
        System.out.println(message);
    }

    @Test
    void goldAmountBiggerThanTotalGrowableCount() {
        final Game game = new Game(5000, 100000000);
        game.addPlayer("Player 1");

        GameCommand command = new ShowBarnCommand(game, ARGS);
        game.start();

        for (PlantType plantType : PlantType.values()) {
            game.getPlayerById(1).getTileMap().getBarn().storeGrowable(new Growable(plantType, 99), 99);
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
        String result = command.flush();
        assertEquals(correctResult, result);
    }

    @Test
    void goldAmountSmallerThanTotalGrowableCount() {
        final Game game = new Game(5, 100000000);
        game.addPlayer("Player 1");

        GameCommand command = new ShowBarnCommand(game, ARGS);
        game.start();

        for (PlantType plantType : PlantType.values()) {
            game.getPlayerById(1).getTileMap().getBarn().storeGrowable(new Growable(plantType, 249), 249);
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
        String result = command.flush();
        assertEquals(correctResult, result);
    }


    @Test
    void barnWithDifferingGrowableCounts() {
        final Game game = new Game(5, 100000000);
        game.addPlayer("Player 1");

        GameCommand command = new ShowBarnCommand(game, ARGS);
        game.start();

        Growable carrot = new Growable(PlantType.CARROT, 99);
        Growable mushroom = new Growable(PlantType.MUSHROOM, 99);
        Growable salad = new Growable(PlantType.SALAD, 99);
        Growable tomato = new Growable(PlantType.TOMATO, 99);

        game.getCurrentPlayer().getTileMap().getBarn().storeGrowable(carrot, 99);
        game.getCurrentPlayer().getTileMap().getBarn().storeGrowable(mushroom, 99);
        game.getCurrentPlayer().getTileMap().getBarn().storeGrowable(salad, 49);
        game.getCurrentPlayer().getTileMap().getBarn().storeGrowable(tomato, 49);

        final String correctResult = "Barn (spoils in 6 turns)\n" +
                "carrots:   100\n" +
                "mushrooms: 100\n" +
                "salads:     50\n" +
                "tomatoes:   50\n" +
                "--------------\n" +
                "Sum:       300\n\n" +
                "Gold:        5";

        command.execute();
        String result = command.flush();
        assertEquals(correctResult, result);
    }


    @Test
    void barnWithoutSomeGrowablesAndNotEmpty() {
        final Game game = new Game(5, 100000000);
        game.addPlayer("Player 1");

        GameCommand command = new ShowBarnCommand(game, ARGS);
        game.start();
        Barn barn = game.getCurrentPlayer().getTileMap().getBarn();
        barn.spoil();

        Growable carrot = new Growable(PlantType.CARROT, 999);
        Growable mushroom = new Growable(PlantType.MUSHROOM, 999);
        Growable salad = new Growable(PlantType.SALAD, 999);
        Growable tomato = new Growable(PlantType.TOMATO, 999);

        barn.storeGrowable(carrot, 50);
        barn.storeGrowable(mushroom, 100);

        final String correctResult = "Barn (spoils in 6 turns)\n" +
                "mushrooms: 100\n" +
                "carrots:    50\n" +
                "--------------\n" +
                "Sum:       150\n\n" +
                "Gold:        5";

        command.execute();
        String result = command.flush();
        assertEquals(correctResult, result);
    }

    @Test
    void emptyBarn() {
        final Game game = new Game(5, 100000000);
        game.addPlayer("Player 1");

        GameCommand command = new ShowBarnCommand(game, ARGS);
        game.start();
        game.getCurrentPlayer().getTileMap().getBarn().spoil();

        final String correctResult = "Barn\n" +
                "Gold: 5";

        command.execute();
        String result = command.flush();
        assertEquals(correctResult, result);
    }
}