package model.time;

import model.event.Event;

public interface Updateable {
    public Event update();
    public boolean active();

}
