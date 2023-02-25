package io;

import io.session.PlaySession;
import io.session.Session;
import model.Game;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.fail;

class PlaySessionTest {

    private final InputStream sysInBackup = System.in; // backup System.in to restore it later

    @Test
    void testRecoginitionOfRegisteredCommands() {

        final String[] commands = new String[]{
                "sell", "buy vegetable", "quit", "show market",
                "show barn", "show board", "buy land", "harvest", "plant", "end turn"};


        for (final String command : commands) {
            final ByteArrayInputStream in = new ByteArrayInputStream(command.getBytes());
            final Scanner scanner = new Scanner(in);
            final Game game = new Game();
            final Session session = new PlaySession(game, scanner);
            session.start();
        }
    }

    @Test
    void testRecoginitionOfUnregisteredSetupCommands() {

        final String[] commands = new String[]{
                "add player", "set player count", "set starting gold", "set winning gold", "shuffle tiles", "show map"
        };

        for (final String command : commands) {
            final ByteArrayInputStream in = new ByteArrayInputStream(command.getBytes());
            final Scanner scanner = new Scanner(in);
            final Game game = new Game();
            final Session session = new PlaySession(game, scanner);
            try {
                session.start();
            } catch (final IllegalArgumentException e) {
                continue;
            }
            fail("Should not be able to execute setup commands in play session");
        }
    }



}