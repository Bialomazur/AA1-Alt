package io.session;

import command.GameCommand;
import command.setup.AddPlayerCommand;
import command.setup.SetPlayerCountCommand;
import command.setup.SetStartingGoldCommand;
import command.setup.SetWinningGoldCommand;
import command.setup.SetTileSeedCommand;
import io.CommandWord;
import model.game.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class ConfigurationSession extends Session {
    private boolean terminatedPrematurily = false;
    private static final int PLAYER_NUMBER_INCREMENT = 1;
    private static final String HOW_MANY_PLAYERS = "How many players?";
    private static final String ENTER_PLAYER_NAME = "Enter the name of player %d:";
    private static final String HOW_MUCH_STARTING_GOLD = "With how much gold should each player start?";
    private static final String HOW_MUCH_WINNING_GOLD = "With how much gold should a player win?";
    private static final String ENTER_SEED = "Please enter the seed to shuffle the tiles:";

    private static final int SETUP_DIALOG_ENTRY_SIZE = 2;
    private static final int SETUP_DIALOG_MESSAGE_INDEX = 0;
    private static final int SETUP_DIALOG_COMMAND_INDEX = 1;
    private final Queue<String[]> setupDialog = new LinkedList<>();

    public ConfigurationSession(final Game game, final Scanner inputScanner) {
        super(game, inputScanner);
    }

    private boolean checkForTerminationRequest(final String input) {
        this.terminatedPrematurily = input.equals(CommandWord.QUIT.getWord());
        if (this.terminatedPrematurily) {
            this.setupDialog.clear();
        }
        return this.terminatedPrematurily;
    }

    private void runDialogEntry(final String[] dialog) {
        this.logInfo(dialog[SETUP_DIALOG_MESSAGE_INDEX]);
        final String commandString = dialog[SETUP_DIALOG_COMMAND_INDEX];
        while (true) {
            final String input = this.nextLine();

            if (this.checkForTerminationRequest(input)) {
                return;
            }

            try {
                final GameCommand gameCommand = this.buildCommand(commandString, List.of(input));
                gameCommand.execute();
                return;
            } catch (final IllegalArgumentException | IllegalStateException e) {
                this.logError(e.getMessage());
            }
        }
    }

    @Override
    protected void init() {
        this.registerCommand(CommandWord.SET_STARTING_GOLD.getWord(), new SetStartingGoldCommand(this.getGame()));
        this.registerCommand(CommandWord.SET_WINNING_GOLD.getWord(), new SetWinningGoldCommand(this.getGame()));
        this.registerCommand(CommandWord.SHUFFLE_TILES.getWord(), new SetTileSeedCommand(this.getGame()));
        this.registerCommand(CommandWord.ADD_PLAYER.getWord(), new AddPlayerCommand(this.getGame()));
        this.registerCommand(CommandWord.SET_PLAYER_COUNT.getWord(), new SetPlayerCountCommand(this.getGame()));

        this.runDialogEntry(new String[]{HOW_MANY_PLAYERS, CommandWord.SET_PLAYER_COUNT.getWord()});
        for (int i = 0; i < this.getGame().getLobby().getLobbySize(); i++) {
            final String[] dialog = new String[SETUP_DIALOG_ENTRY_SIZE];
            dialog[SETUP_DIALOG_MESSAGE_INDEX] = String.format(ENTER_PLAYER_NAME, i + PLAYER_NUMBER_INCREMENT);
            dialog[SETUP_DIALOG_COMMAND_INDEX] = CommandWord.ADD_PLAYER.getWord();
            this.setupDialog.add(dialog);
        }
        this.setupDialog.add(new String[]{HOW_MUCH_STARTING_GOLD, CommandWord.SET_STARTING_GOLD.getWord()});
        this.setupDialog.add(new String[]{HOW_MUCH_WINNING_GOLD, CommandWord.SET_WINNING_GOLD.getWord()});
        this.setupDialog.add(new String[]{ENTER_SEED, CommandWord.SHUFFLE_TILES.getWord()});
    }

    @Override
    public void start() {
        this.init();

        while (!this.setupDialog.isEmpty() && !this.terminatedPrematurily) {
            final String[] dialog = this.setupDialog.poll();
            this.runDialogEntry(dialog);
        }
    }

    public boolean hasBeenTerminatedPrematurily() {
        return this.terminatedPrematurily;
    }
}