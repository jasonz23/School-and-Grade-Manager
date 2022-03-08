package tests.test;

import model.Event;
import model.EventLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {
    private Event event1;
    private Event event2;
    private Event event3;

    @BeforeEach
    public void loadEvents() {
        event1 = new Event("Event1");
        event2 = new Event("Event2");
        event3 = new Event("Event3");
        EventLog el = EventLog.getInstance();
        el.logEvent(event1);
        el.logEvent(event2);
        el.logEvent(event3);
    }

    @Test
    public void testLogEvent() {
        List<Event> l = new ArrayList<Event>();

        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }

        assertTrue(l.contains(event1));
        assertTrue(l.contains(event2));
        assertTrue(l.contains(event3));
    }

    @Test
    public void testClear() {
        EventLog el = EventLog.getInstance();
        el.clear();
        Iterator<Event> itr = el.iterator();
        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}
