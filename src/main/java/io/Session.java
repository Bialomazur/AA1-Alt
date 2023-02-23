package io;

import command.AddPlayerCommand;
import command.GameCommand;
import command.GameCommandFactory;
import command.action.HarvestCommand;
import command.action.PlantGrowableCommand;
import command.action.SellGrowableCommand;
import command.flow.EndTurnCommand;
import command.flow.QuitGameCommand;
import command.info.ShowMapCommand;
import model.Game;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

//TODO: Consider making this the main class
//TODO: Consider using 2 separate classes with strategy pattern for loggers
public class Session {
    private final Game game;
    private final MainLogger logger = new MainLogger();
    private final GameCommandFactory commandFactory;
    private final Scanner scanner = new Scanner(System.in); //TODO: Is it OK to init here?

    private static final int MIN_STARTING_GOLD = 0;
    private static final int MIN_WINNING_GOLD = 1;

    private static final int MIN_PLAYER_COUNT = 1;
    private static final int PLAYER_NUMBER_INCREMENT = 1;
    private static final String HOW_MANY_PLAYERS = "How many players?";
    private static final String ENTER_PLAYER_NAME = "Enter the name of player %d:";
    private static final String HOW_MUCH_STARTING_GOLD = "With how much gold should each player start?";
    private static final String HOW_MUCH_WINNING_GOLD = "With how much gold should a player win?";
    private static final String ENTER_SEED = "Please enter the seed to shuffle the tiles:";
    private static final String ARGUMENT_SEPARATOR = " ";

    private static final String UNREGISTERED_COMMAND = "The given command is not registered.";
    private static final String INVALID_PLAYER_COUNT = "Invalid number of players.";
    private static final String TOO_LITTLE_GOLD = "The needed gold must at least be %d.";
    private static final String INVALID_GOLD_NUMBER_FORMAT = "Gold must be an integer.";

    public Session(final Game game) {
        this.game = game;
        this.commandFactory = new GameCommandFactory(game);
    }

    private void init() {
        this.commandFactory.registerCommand(CommandWord.QUIT.getWord(), new QuitGameCommand(this.game));
        this.commandFactory.registerCommand(CommandWord.ADD_PLAYER.getWord(), new AddPlayerCommand(this.game));
        this.commandFactory.registerCommand(CommandWord.HARVEST.getWord(), new HarvestCommand(this.game));
        this.commandFactory.registerCommand(CommandWord.PLANT.getWord(), new PlantGrowableCommand(this.game));
        this.commandFactory.registerCommand(CommandWord.SHOW_MAP.getWord(), new ShowMapCommand(this.game));
        this.commandFactory.registerCommand(CommandWord.END_TURN.getWord(), new EndTurnCommand(this.game));
        this.commandFactory.registerCommand(CommandWord.SELL.getWord(), new SellGrowableCommand(this.game));
    }

    private String parseCommand(final String input) {
        final Set<String> registeredCommands = this.commandFactory.getRegisteredCommands().keySet();
        final List<String> separatedContent = List.of(input.split(ARGUMENT_SEPARATOR));

        for (final String command : registeredCommands) {
            if (separatedContent.contains(command)) {
                return command;
            }
        }

        throw new IllegalArgumentException(UNREGISTERED_COMMAND);
    }

    private int getPlayerCount() {
        int playerCount = 0;
        while (true) {
            try {
                playerCount = Integer.parseInt(this.scanner.nextLine());
            } catch (final NumberFormatException e) {
                this.logger.logError(INVALID_PLAYER_COUNT);
            }

            if (playerCount < MIN_PLAYER_COUNT) {
                this.logger.logError(INVALID_PLAYER_COUNT);
                continue;
            }
            return playerCount;
        }
    }


    //TODO: Rigorous testing!
    private int getGold(final int minGold) {
        int gold = 0;
        while (true) {
            try {
                gold = Integer.parseInt(this.scanner.nextLine());
            }  catch (final NumberFormatException e) {
                this.logger.logError(INVALID_GOLD_NUMBER_FORMAT);
            }

            if (gold < minGold) {
                this.logger.logError(TOO_LITTLE_GOLD.formatted(minGold));
                continue;
            }

            return gold;
        }
    }



    private void registerPlayer() {
        while(true) {
            try {
                final String playerName = this.scanner.nextLine();
                final GameCommand command = this.commandFactory.getRegisteredCommands().get(CommandWord.ADD_PLAYER.getWord());
                command.setArgs(List.of(playerName));
                command.execute();
            } catch (final IllegalArgumentException e) {
                this.logger.logError(e.getMessage());
                continue;
            }
            return;
        }
    }


    private void setup() {
        this.logger.log(HOW_MANY_PLAYERS);
        final int playerCount = this.getPlayerCount();

        for (int i = 0; i < playerCount; i++) {
            this.logger.log(String.format(ENTER_PLAYER_NAME, i + PLAYER_NUMBER_INCREMENT));
            this.registerPlayer();
        }

        this.logger.log(HOW_MUCH_STARTING_GOLD);
        this.getGold(MIN_STARTING_GOLD);
        this.logger.log(HOW_MUCH_WINNING_GOLD);
        final int winningGold = this.getGold(MIN_WINNING_GOLD);
        //TODO: Finish io setup



    }

    public void start() {
        this.init();
        this.setup();
        //noinspection IOResourceOpenedButNotSafelyClosed //TODO Reconsinder this comment
        this.game.start();

        while (this.game.isRunning()) {
            final String input = this.scanner.nextLine();


        }
    }
}