package model;

import model.exception.GameStartedWithoutPlayersException;
import model.market.Market;
import model.time.MasterClock;

import java.util.HashSet;
import java.util.Set;


// TODO: Consider outsourcing some information to a GameConfig class.

public class Game {
    private final MasterClock masterClock = new MasterClock();
    private final Market market = new Market();
    private final Set<Player> players = new HashSet<>();
    private int winningGold = 0;
    private int startingGold = 0;
    private int playerCount = 0;
    private boolean running = false;
    private static final int PLAYER_ID_INCREMENT = 1;

    private void init() {
        this.masterClock.setTurnsPerRound(this.playerCount);

        for (final Player player : this.players) {
            player.getTileMap().init();
        }
    }

    private int generatePlayerId() {
        return this.players.size() + PLAYER_ID_INCREMENT;
    }

    public void addPlayer(final String name) {
        this.players.add(new Player(name, this.startingGold, this.generatePlayerId()));
    }

    private Set<Player> getPlayersWithWinningGoldAmount() {
        final Set<Player> playersWithWinningGoldAmount = new HashSet<>();
        for (final Player player : this.players) {
            if (player.getGold() >= this.winningGold) {
                playersWithWinningGoldAmount.add(player);
            }
        }
        return playersWithWinningGoldAmount;
    }

    public void start() {
        this.init();
        this.running = true;
    }

    public void quit() {
        this.running = false;
    }

    //TODO: consider calling this nextTurn() instead for more consistency with nextAction().
    public void endTurn() {
        this.market.updatePrices();
      //  this.masterClock.nextTurn(); TODO: Fix this through implementing the new master clock & time system.

        if (this.masterClock.newRoundStarted() && !this.getPlayersWithWinningGoldAmount().isEmpty()) {
            this.quit();
        }
    }

    public Player getCurrentPlayer() {
        final int currentPlayerId = this.masterClock.getTurn();

        for (final Player player : this.players) {
            if (player.getId() == currentPlayerId) { //TODO: Check whether not to use hashCode() here.
                return player;
            }
        }

        throw new GameStartedWithoutPlayersException(); // TODO: Reconsider creating an extra exception for this.
    }

    public void nextAction() {
        this.masterClock.nextAction();
    }

    // TODO: Reconsider this method
    public Player getPlayerById(final int id) {
        for (final Player player : this.players) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    public void setStartingGold(final int startingGoldAmount) {
        this.startingGold = startingGoldAmount;
    }

    public void setWinningGold(final int winningGoldAmount) {
        this.winningGold = winningGoldAmount;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setPlayerCount(final int playerCount) {
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }
}
