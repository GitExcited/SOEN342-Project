package main;

import java.time.LocalTime;

// Event class to encapsulate start and end times
public class TimeSlot {
    private String dayOfWeek;
    private LocalTime start;
    private LocalTime end;

    public TimeSlot(String dayOfWeek, LocalTime start, LocalTime end) {
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setdayOfWeek(String day) {
        this.dayOfWeek = day;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
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
    public boolean collides(TimeSlot other) {
        return !this.end.isBefore(other.start) && !this.start.isAfter(other.end);
    }

    // Method to convert TimeSlot to JSON string
    public String toJson() {
        return String.format("{\"dayOfWeek\":\"%s\",\"start\":\"%s\",\"end\":\"%s\"}",
                dayOfWeek, start.toString(), end.toString());
    }
    
}