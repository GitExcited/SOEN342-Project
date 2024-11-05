package main;

public class PublicOffering {
    private Offering offering;
    private Instructor instructor;
    private boolean isAvailable;

    public PublicOffering(Offering offering, Instructor instructor){
        this.offering = offering;
        this.instructor = instructor;
        this.isAvailable = true;
    }

    public void makeUnavailable(){
        this.isAvailable = false;
    }

    public boolean getAvailability(){
        return isAvailable;
    }

    public String toString(){
        return "Offering: " + offering.toString()+ " Instructor: " + instructor.toString();
    }
}
