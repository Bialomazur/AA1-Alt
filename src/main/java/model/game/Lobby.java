package model.game;

import java.util.Arrays;
import java.util.List;

public class Lobby {
    private static final int PLAYER_ID_OFFSET = 1;
    private final Player[] players;
    private static final String NO_PLAYER_WITH_ID = "No player with id %d found.";
    private static final String LOBBY_IS_FULL = "Lobby is full.";

    public Lobby(final int playerCount) {
        this.players = new Player[playerCount];
    }

    public void addPlayer(final String name) {
        for (int i = 0; i < this.players.length; i++) {
            if (this.players[i] == null) {
                this.players[i] = new Player(name, i + PLAYER_ID_OFFSET);
                return;
            }
        }
        throw new IllegalStateException(LOBBY_IS_FULL);
    }

    //TODO: Consider not throwing an exception and returning a null object if player not in lobby.
    public Player getPlayerById(final int id) {
        return this.players[id - PLAYER_ID_OFFSET];
    }

    public List<Player> getPlayers() {
        return Arrays.asList(this.players);
    }

    public int getLobbySize() {
        return this.players.length;
    }
}
