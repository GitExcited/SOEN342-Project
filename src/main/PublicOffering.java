package main;

public class PublicOffering extends Offering {
    private Instructor instructor;

    // Constructor
    public PublicOffering(Lesson lesson, Event event, Instructor instructor, Location location) {
        super(lesson, event,location);
        this.instructor = instructor;
    }
    //Constructor if Offering is being transformed to PublicOffering
    public PublicOffering(Offering offering, Instructor instructor){
        super(offering.getLesson(),offering.getLessonTime(),offering.getLocation());
        this.instructor= instructor;
    }

    // Getter for instructor
    public Instructor getInstructor() {
        return instructor;
    }

    // Setter for instructor
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "PublicOffering{" +
                "lesson=" + getLesson() +
                ", event=" + getLessonTime() +
                ", instructor=" + instructor +
                '}';
    }
}
