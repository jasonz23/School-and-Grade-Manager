package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class EventLog implements Iterable<Event> {

    private static EventLog theLog;
    private Collection<Event> events;

    private EventLog() {
        events = new ArrayList<Event>();
    }

    //EFFECT: get instances of eventLog is it doesn't already exist (singleton method)
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    //MODIFIES: this
    //EFFECTS: add event to events
    public void logEvent(Event e) {
        events.add(e);
    }

    //MODIFIES: this
    //EFFECT: remove all in event and add a new event with the description "event log cleared"
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    //EFFECT: return events
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
