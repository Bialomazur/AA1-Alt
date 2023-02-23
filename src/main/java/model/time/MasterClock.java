package model.time;

import model.event.Event;
import model.event.EventLog;
import model.event.LogEntry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


//TODO !!!! Overthink not making turnsPerRound and actionsPerTurn final.

public class MasterClock implements Clock {
    private final Map<Integer, Set<Countdown>> countdowns = new HashMap<>();
    private final Set<Clock> clocks = new HashSet<>();
    private final EventLog eventLog = new EventLog();
    private int turn = STARTING_TURN;
    private int action = STARTING_ACTION;
    private int turnsPerRound = DEFAULT_TURNS_PER_ROUND;
    private int actionsPerTurn = DEFAULT_ACTIONS_PER_TURN;
    private static final int STARTING_TURN = 1; //TODO: Verify whether this number should be coupled to the starting id of players since it is used to determine the current player.
    private static final int STARTING_ACTION = 1;
    private static final int DEFAULT_ACTIONS_PER_TURN = 2;
    private static final int DEFAULT_TURNS_PER_ROUND = 1;


    public void subscribe(final Countdown countdown) {
        this.countdowns.get(turn).add(countdown);
    }

    public void nextAction() {
        this.action++;
        if (this.action > this.actionsPerTurn) {
            tick();
        }
    }

    //TODO:!!!! Make DAMN SURE that the logs get added to the event log with the CORRECT player id.
    //TODO: Consider refactoring due to Arrowhead code smell.
    private void nextRound() {
        for (final Set<Countdown> countdowns : this.countdowns.values()) {
            for (final Countdown countdown : countdowns) {
                countdown.count();
                final Event log = countdown.flush();

                if (!log.equals(LogEntry.EMPTY_LOG.format())) {
                    //this.eventLog.addLogEntry(turn, log);
                }
            }
        }
    }

    public int getTurn() {
        return this.turn;
    }

    public void setTurnsPerRound(final int turnsPerRound) {
        this.turnsPerRound = turnsPerRound;
    }

    public void setActionsPerTurn(final int actionsPerTurn) {
        this.actionsPerTurn = actionsPerTurn;
    }

    public boolean newRoundStarted() {
        return this.turn == STARTING_TURN;
    }

    public void init(int turnsPerRound) {
        for (int i = 0; i < turnsPerRound; i++) {
            this.countdowns.put(i, new HashSet<>());
        }

        this.setTurnsPerRound(turnsPerRound);
    }


    //START NEXT TURN
    @Override
    public void tick() {
        this.action = STARTING_ACTION;
        this.turn++;
        if (this.turn > this.turnsPerRound) {
            this.turn = STARTING_TURN;
            nextRound();
        }
    }
}
