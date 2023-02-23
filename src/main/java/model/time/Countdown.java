package model.time;

// TODO !!!! Fix DRY-Violation of using target in multiple countdown subclasses
// TODO Fix those ugly variable names

import model.event.Event;
import model.event.EventType;

public abstract class Countdown {
    private boolean active;
    private final int countDelta;
    private int currentCount;
    private final int defaultCount;
    private Event event;
    private static final int COUNT_LOWER_BOUNDARY = 0;
    private static final int DEFAULT_COUNT_RETURN = 0;
    private static final boolean SHOULD_ACTIVATE_ON_DEFAULT = false;

    protected Countdown(final int defaultCount, final int countDelta) {
        this.defaultCount = defaultCount;
        this.countDelta = countDelta;
        this.active = true;
    }

    void resetCountdown() {
        this.currentCount = this.defaultCount;
    }

    void deactivate() {
        this.active = false;
    }

    /**
     * private since only a countdown should be able to re-activate itself.
     */
    private void activate() {
        this.active = true;
    }

    boolean isActive() {
        return this.active;
    }


    //TODO: Fix this fishy method
    //TODO: Provide SpoilCountdown with capability to check for itself whether it should activate itself or not.
    int count() {
        if (!this.active && this.shouldActivate()) {
            this.activate();
        }

        if (!this.active) {
            return DEFAULT_COUNT_RETURN;
        }

        this.currentCount += this.countDelta; // count "down"

        // check if countdown finished
        if (this.currentCount <= COUNT_LOWER_BOUNDARY || this.currentCount > this.defaultCount) {
            return this.execute();
        }

        return DEFAULT_COUNT_RETURN;
    }

    Event flush() {
        final Event event = this.event;
        this.event = new Event(EventType.NOTHING, DEFAULT_COUNT_RETURN);
        return event;
    }

    protected void setEvent(final Event event) {
        this.event = event;
    }

    protected abstract int execute();

    /**
     * Hook-Method allowing for countdowns in an in-active state to check whether they should activate themselves.
     * Does nothing by default.
     */

    protected boolean shouldActivate() {
        return SHOULD_ACTIVATE_ON_DEFAULT;
    }

    int getCurrentCount() {
        return this.currentCount;
    }
}
