package model.time;

import model.event.Event;
import model.event.LogEntry;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CountdownClock implements Clock {
    private final Set<Countdown> countdowns = new HashSet<>();
    private final Queue<Event> eventQueue = new LinkedList<>();
    @Override
    public void tick() {
        for (final Countdown countdown : countdowns) {
            countdown.count();
            final Event event = countdown.flush();

            if (!event.equals(LogEntry.EMPTY_LOG.format())) {
                this.eventQueue.add(event);
            }
        }
    }

    public void subscribe(final Countdown countdown) {
        this.countdowns.add(countdown);

    }

}
