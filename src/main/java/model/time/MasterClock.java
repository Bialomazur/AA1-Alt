package model.time;

import model.event.Event;
import model.event.EventLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


//TODO !!!! Overthink not making turnsPerRound and actionsPerTurn final.

public class MasterClock implements Clock {
    private final Map<Integer, List<Updateable>> elementsToUpdate = new HashMap<>();
    private final EventLog eventLog = new EventLog();
    private int turn = STARTING_TURN;
    private int action = STARTING_ACTION;
    private final int turnsPerRound;
    private static final int STARTING_TURN = 1; //TODO: Verify whether this number should be coupled to the starting id of players since it is used to determine the current player.
    private static final int STARTING_ACTION = 1;
    private static final int ACTIONS_PER_TURN = 2;

    public MasterClock(final int turnsPerRound) {
        this.turnsPerRound = turnsPerRound;
    }

    public void nextAction() {
        this.action++;
        if (this.action > ACTIONS_PER_TURN) {
            this.tick();
        }
    }

    //TODO:!!!! Make DAMN SURE that the logs get added to the event log with the CORRECT player id.
    //TODO: Consider refactoring due to Arrowhead code smell.
    private void nextRound() {
        for (final Entry<Integer, List<Updateable>> playerUpdateables : this.elementsToUpdate.entrySet()) {
            for (final Updateable updateable : playerUpdateables.getValue()) {
                final Event triggeredEvent = updateable.update();
                this.eventLog.add(playerUpdateables.getKey(), triggeredEvent);
            }
        }
    }

    public int getTurn() {
        return this.turn;
    }


    public boolean newRoundStarted() {
        return this.turn == STARTING_TURN;
    }

    public void init(final int turnsPerRound) {
    }

    public void addListOfElementsToUpdate(final List<? extends Updateable> elements, final int playerId) {
        this.elementsToUpdate.get(playerId).addAll(elements);
    }

    public void addElementToUpdate(final Updateable element, final int playerId) {
        this.elementsToUpdate.get(playerId).add(element);
    }

    //START NEXT TURN
    @Override
    public void tick() {
        this.action = STARTING_ACTION;
        this.turn++;
        if (this.turn > this.turnsPerRound) {
            this.turn = STARTING_TURN;
            this.nextRound();
        }
    }

    public void init() {
        for (int i=0; i<this.turnsPerRound; i++) {
            this.elementsToUpdate.put(i+1, new ArrayList<>());
        }
    }
}
