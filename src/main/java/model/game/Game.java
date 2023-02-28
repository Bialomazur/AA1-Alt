package model.game;

import model.map.TileDispatcher;
import model.market.Market;
import model.time.Clock;
import model.time.MasterClock;

import java.util.LinkedList;
import java.util.List;

public class Game {
    private Lobby lobby;
    private MasterClock masterClock;
    private Market market;
    private TileDispatcher tileDispatcher;
    private int startingGold;
    private int winningGold;
    private int shuffleSeed;

    private boolean running = false;


    private void init() {
        this.masterClock = new MasterClock(this.lobby.getLobbySize());
        this.masterClock.init();

        for (final Player player : this.lobby.getPlayers()) {
            player.setGold(this.startingGold);
            player.getTileMap().init();
            player.getTileMap().getBarn().init();
            this.masterClock.addListOfElementsToUpdate(player.getTileMap().getTiles(), player.getId());
        }

        this.tileDispatcher = new TileDispatcher(this.lobby.getLobbySize());
        this.tileDispatcher.init();
        this.tileDispatcher.shuffleTiles(this.shuffleSeed);
        this.running = true;
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    public MasterClock getMasterClock() {
        return this.masterClock;
    }

    public Market getMarket() {
        return this.market;
    }

    public TileDispatcher getTileDispatcher() {
        return this.tileDispatcher;
    }

    public void start() {
        this.init();
    }

    private List<Player> getWinners() {
        final List<Player> winners = new LinkedList<>();

        for (final Player player : this.lobby.getPlayers()) {
            if (player.getGold() >= this.winningGold) {
                winners.add(player);
            }
        }
        return winners;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void quit() {
        this.running = false;
    }

    public void addPlayer(final String name) {
        this.lobby.addPlayer(name);
    }

    public Player getPlayerById(final int id) {
        return this.lobby.getPlayerById(id);
    }

    public void createLobby(final int lobbySize) {
        this.lobby = new Lobby(lobbySize);
    }

    public void setStartingGold(final int startingGold) {
        this.startingGold = startingGold;
    }

    public void setWinningGold(final int winningGold) {
        this.winningGold = winningGold;
    }

    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public Player getCurrentPlayer() {
        final int currentPlayersId = this.masterClock.getTurn();
        return this.lobby.getPlayerById(currentPlayersId);
    }

}
