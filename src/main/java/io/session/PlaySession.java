package io.session;

import command.action.BuyGrowableCommand;
import command.action.BuyLandCommand;
import command.action.EndTurnCommand;
import command.action.HarvestCommand;
import command.action.PlantGrowableCommand;
import command.action.QuitGameCommand;
import command.action.SellGrowableCommand;
import command.info.ShowBarnCommand;
import command.info.ShowBoardCommand;
import command.info.ShowMarketCommand;
import io.CommandWord;
import model.Game;

import java.util.List;
import java.util.Scanner;

public class PlaySession extends Session{
    private static final String INPUT_SEPARATOR = " ";
    private static final String UNREGISTERED_COMMAND = "The given command is not registered.";

    public PlaySession(final Game game, final Scanner inputScanner) {
        super(game, inputScanner);
    }

    @Override
    public void start() {
        this.init();
        final Game game = this.getGame();
        //game.start();

        final String inputt = this.nextLine();
        final String commandWordd = this.parseCommandWord(inputt);


        while (game.isRunning()) {
            final String input = this.nextLine();
            final String commandWord = this.parseCommandWord(input);

            return;
        }

    }


    //TODO: Make sure that all NEEDED commands are registered
    @Override
    protected void init() {
        this.registerCommand(CommandWord.QUIT.getWord(), new QuitGameCommand(this.getGame()));
        this.registerCommand(CommandWord.HARVEST.getWord(), new HarvestCommand(this.getGame()));
        this.registerCommand(CommandWord.PLANT.getWord(), new PlantGrowableCommand(this.getGame()));
        this.registerCommand(CommandWord.END_TURN.getWord(), new EndTurnCommand(this.getGame()));
        this.registerCommand(CommandWord.SELL.getWord(), new SellGrowableCommand(this.getGame()));
        this.registerCommand(CommandWord.SHOW_BARN.getWord(), new ShowBarnCommand(this.getGame()));
        this.registerCommand(CommandWord.SHOW_BOARD.getWord(), new ShowBoardCommand(this.getGame()));
        this.registerCommand(CommandWord.SHOW_MARKET.getWord(), new ShowMarketCommand(this.getGame()));
        this.registerCommand(CommandWord.BUY_VEGETABLE.getWord(), new BuyGrowableCommand(this.getGame()));
        this.registerCommand(CommandWord.BUY_LAND.getWord(), new BuyLandCommand(this.getGame()));
    }

    private List<String> parseArguments(final String input) {
        final String[] splitInput = input.split(INPUT_SEPARATOR);
        final int inputWords = splitInput.length;

        if (inputWords == 1) {
            return List.of();
        }

        return List.of(splitInput).subList(1, inputWords);
    }


    private String parseCommandWord(final String input) {
        final String[] splitInput = input.split(INPUT_SEPARATOR);
        final StringBuilder commandWord = new StringBuilder();

        for (final String word : splitInput) {
            commandWord.append(word);

            if (this.isRegistered(commandWord.toString())) {
                return commandWord.toString();
            }
            commandWord.append(INPUT_SEPARATOR);
        }

        throw new IllegalArgumentException(UNREGISTERED_COMMAND);
    }
}
