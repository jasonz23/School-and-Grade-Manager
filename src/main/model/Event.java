package model;

import java.util.Calendar;
import java.util.Date;

public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    //EFFECT: assign the date to current date and set description
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    //EFFECT: returns date
    public Date getDate() {
        return dateLogged;
    }

    //EFFECT: returns description
    public String getDescription() {
        return description;
    }

    //EFFECT: return true if the event is equal to the object
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged) && this.description.equals(otherEvent.description));
    }

    //EFFECT: return hashCode
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    //EFFECT: return date logged and description as string
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
