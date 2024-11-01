package main;

import java.time.LocalDateTime;

// Event class to encapsulate start and end times
public class Event {
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

       /**
     * Checks if this event collides with another event.
     * 
     * @param other The other event to check for collision.
     * @return true if the events collide, false otherwise.
     */
    public boolean collides(Event other) {
        return !this.end.isBefore(other.start) && !this.start.isAfter(other.end);
    }
    
}