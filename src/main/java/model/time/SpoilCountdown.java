package model.time;

import model.event.Event;
import model.event.EventType;
import model.map.Barn;

public class SpoilCountdown extends Countdown {
    private final Barn target;

    protected SpoilCountdown(final int defaultCount, final int countDelta, final Barn target) {
        super(defaultCount, countDelta);
        this.target = target;
    }

    @Override
    protected boolean shouldActivate() {
        return !this.target.isEmpty();
    }

    @Override
    protected int execute() {
        // spoil barn, reset countdown and deactivate the countdown because afterwards the barn will be empty.
        this.resetCountdown();
        this.deactivate();
        this.setEvent(new Event(EventType.BARN_HAS_SPOILED, this.target.getTotalUnitsStored()));
        this.target.spoil();
        return this.getCurrentCount();
    }
}
