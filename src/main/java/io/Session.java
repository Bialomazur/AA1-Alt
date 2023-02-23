package io;

import command.GameCommandFactory;
import model.Game;

import java.util.Scanner;

//TODO: Consider making this the main class
public class Session {
    private final Game game;
    private final GameCommandFactory commandFactory;
    public Session(final Game game) {
        this.game = game;
        this.commandFactory = new GameCommandFactory(game);
    }

    public Game getGame() {
        return game;
    }

    private void init() {
        this.commandFactory.registerCommand("addPlayer", command.AddPlayerCommand.class);
        this.commandFactory.registerCommand(CommandWord.QUIT.getWord(), command.EndGameCommand.class);
        this.commandFactory.registerCommand(CommandWord.END_TURN.getWord(), command.EndTurnCommand.class);
    }

    private void setup() {

    }

    public void start() {
        this.init();

        //noinspection IOResourceOpenedButNotSafelyClosed //TODO Reconsinder this comment
        final Scanner scanner = new Scanner(System.in);
        this.game.start();

        while (this.game.isRunning()) {
            String input = scanner.nextLine();

        }
    }
}