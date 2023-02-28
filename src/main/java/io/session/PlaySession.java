package io.session;

import command.GameCommand;
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
import model.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaySession extends Session{
    private static final String INPUT_SEPARATOR = " ";
    private static final String UNREGISTERED_COMMAND = "The given command is not registered.";
    private static final int MIN_ARGUMENT_COUNT = 0;
    private static final int INPUT_SPLIT_BY_COMMAND_WORD_ARGUMENTS_INDEX = 1;

    public PlaySession(final Game game, final Scanner inputScanner) {
        super(game, inputScanner);
    }

    @Override
    public void start() {
        this.init();
        final Game game = this.getGame();
        game.start();

        while (game.isRunning()) {
            final String input = this.nextLine();
            try {
                final String commandWord = this.parseCommandWord(input);
                final List<String> arguments = this.parseArguments(input, commandWord);
                final GameCommand command = this.buildCommand(commandWord, arguments);
                command.execute();
                this.logInfo(command.flush());
            } catch (final IllegalArgumentException | IllegalStateException e) {
                this.logError(e.getMessage());
            }
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

    private List<String> parseArguments(final String input, final String parsedCommandWord) {
        final String[] inputSplitByCommandWord = input.split(parsedCommandWord);

        if (inputSplitByCommandWord.length == MIN_ARGUMENT_COUNT) {
            return List.of();
        }

        final List<String> parsedArguments = new ArrayList<>();
        for (final String argument : inputSplitByCommandWord[1].split(INPUT_SEPARATOR)) {
            if (!argument.isEmpty()) {
                parsedArguments.add(argument);
            }
        }

        return parsedArguments;
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
