package model.event;

public class Event {
    private final EventType eventType;
    private final int impact;
    public Event(EventType eventType, int impact) {
        this.eventType = eventType;
        this.impact = impact;
    }

    public EventType getEventType() {
        return eventType;
    }

    public int getImpact() {
        return impact;
    }
}
