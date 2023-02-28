package io.session;

import command.GameCommand;
import command.GameCommandFactory;
import io.log.ErrorLogger;
import io.log.InfoLogger;
import io.log.Logger;
import model.game.Game;

import java.util.List;
import java.util.Scanner;


//TODO: Overthink proxy encapsulation as it might be a dry-violation AND SOLID-violation
//TODO: Due to each update to for instance gameCommandFactory, the proxy needs to be updated as well.

public abstract class Session {
    private final GameCommandFactory gameCommandFactory;
    private final Game game;
    private final Scanner inputScanner; //TODO: Consider letting each Session create its own Scanner

    private final Logger infoLogger = new InfoLogger(System.out);
    private final Logger errorLogger = new ErrorLogger(System.err);

    private final boolean running = false;


    protected Session(final Game game, final Scanner inputScanner) {
        this.game = game;
        this.inputScanner = inputScanner;
        this.gameCommandFactory = new GameCommandFactory(this.game);
    }


    public abstract void start();

    protected abstract void init();

    public boolean isRunning() {
        return this.running;
    }

    protected Game getGame() {
        return this.game;
    }

    protected Scanner getInputScanner() {
        return this.inputScanner;
    }

    protected void registerCommand(final String commandWord, final GameCommand command) {
        this.gameCommandFactory.register(commandWord, command);
    }


    //TODO: Consider if this is a dry-violation
    protected void logInfo(final String message) {
        this.infoLogger.logInfo(message);
    }

    protected void logError(final String message) {
        this.errorLogger.logInfo(message);
    }

    protected String nextLine() {
        return this.inputScanner.nextLine();
    }

    public GameCommand buildCommand(final String commandWord, final List<String> args) {
        return this.gameCommandFactory.buildCommand(commandWord, args);
    }

    protected boolean isRegistered(final String commandWord) {
        return this.gameCommandFactory.isRegistered(commandWord);
    }
}
