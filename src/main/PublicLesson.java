package main;

public class PublicLesson extends Lesson {

    private Boolean isPublic = true;

    // Constructor
    public PublicLesson(String title, String description, Location loc, TimeSlot timeSlot) {
        super( title, description, loc, timeSlot);
    }
    public PublicLesson(Lesson lesson){
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
        return "Public Lesson: " + super.toString();
    }
}