package model.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;


/**
 * Class that is responsible for keeping track of the events that occur after a round passes.
 */

// TODO: Perhaps find a more suiting name in order to make clear that an EventLog is only considered
// TODO: to be responsible for countdown events.
public class EventLog {
    private final Map<Integer, Queue<Event>> logEntries = new HashMap<>();

    public void add(final int playerId, final Event event) {
        this.logEntries.get(playerId).add(event);
    }

    public String flush(final int playerId) {
        final StringBuilder stringBuilder = new StringBuilder();
        while (!this.logEntries.get(playerId).isEmpty()) {
            stringBuilder.append(this.logEntries.get(playerId).poll());
        }
        return stringBuilder.toString();
    }
}
