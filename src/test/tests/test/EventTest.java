package tests.test;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EventTest {
    private Event e;
    private Event e2;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("Added Course");
        e2 = new Event("Added Course");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Added Course", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Added Course", e.toString());
    }

    @Test
    public void testEquals() {
        assertFalse(e.equals(null));
        assertFalse(e.equals(123));
    }

    @Test
    public void testHashCode() {
        assertEquals(e2.hashCode(),e.hashCode());
    }
}
