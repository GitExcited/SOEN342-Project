package main;
public class PrivateLesson extends Lesson {

    private Boolean isPublic = false;
    // Constructor
    public PrivateLesson(String title, String description, Location loc, TimeSlot timeSlot) {
        super(title, description, loc, timeSlot);
    }
    public PrivateLesson(Lesson lesson){
        super(lesson.getTitle(), lesson.getDescription(),lesson.getLocation(),lesson.getTimeSlot());
    }

    @Override
   public Object[] toParams() {
       return new Object[] {
           this.id,
           this.title,
           this.description,
           this.location.getID(),
           this.timeSlot != null ? this.timeSlot.toJson() : null,
           this.isPublic,
       };
   }

    @Override
    public String toString() {
        return "Private Lesson: " + super.toString();
    }
}