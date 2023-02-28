package model.event;

public class Event {
    private EventType eventType;
    private int impact;

    private static final int DEFAULT_IMPACT = 0;
    private static final EventType DEFAULT_EVENT_TYPE = EventType.NOTHING;

    public Event(EventType eventType, int impact) {
        this.eventType = eventType;
        this.impact = impact;
    }

    public Event(){
        this.eventType = DEFAULT_EVENT_TYPE;
        this.impact = DEFAULT_IMPACT;
    }

    public EventType getEventType() {
        return eventType;
    }

    public int getImpact() {
        return impact;
    }

    public void setImpact(int impact) {
        this.impact = impact;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
