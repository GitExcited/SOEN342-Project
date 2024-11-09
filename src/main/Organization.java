package main;

import java.util.ArrayList;

public class Organization {
    private ArrayList<Location> locationCollection = new ArrayList<Location>();
    

    public Organization(){

    }

    public void initializeLocations(){
        locationCollection.add(new Location("1", "City Sports Complex", "123 Main St", "Springfield", "Room 101"));
        locationCollection.add(new Location("2", "Downtown Gym", "456 Maple Ave", "Riverside", "Studio B"));
        locationCollection.add(new Location("3", "Westside Pool", "789 Oak Rd", "Lakeside", "Pool 1"));
        locationCollection.add(new Location("4", "East End Fitness Center", "321 Pine Ln", "Hilltown", "Room 305"));
        locationCollection.add(new Location("5", "Central Park Courts", "654 Elm St", "Greenfield", "Court 2"));
        locationCollection.add(new Location("6", "Northside Yoga Studio", "987 Birch Dr", "Meadowville", "Studio A"));
        locationCollection.add(new Location("7", "Southtown Boxing Club", "111 Cedar Pl", "Brookfield", "Room 12"));
        locationCollection.add(new Location("8", "Uptown Dance Hall", "222 Willow Rd", "Ridgeview", "Main Hall"));
        locationCollection.add(new Location("9", "Valley Martial Arts", "333 Aspen Ct", "Riverbend", "Dojo Room"));
        locationCollection.add(new Location("10", "Lakeside Track & Field", "444 Poplar Ave", "Clearwater", "Field A"));
    }

    //Load sample lesson data
    // public void initializeLessons(){
    //     lessonCollection.add(new PublicLesson("1", "Soccer", "Learn the basics of soccer, including rules and techniques."));
    //     lessonCollection.add(new PublicLesson("2","Basketball", "An overview of basketball skills and gameplay strategies."));
    //     lessonCollection.add(new PublicLesson("3","Tennis", "Basics of tennis, including serves, volleys, and backhands."));
    //     lessonCollection.add(new PublicLesson("4","Swimming", "Learn swimming techniques, strokes, and breathing exercises."));
    //     lessonCollection.add(new PrivateLesson("5","Running Mechanics", "Improve your running form and understand endurance training."));
    //     lessonCollection.add(new PrivateLesson("6","Yoga", "Basic yoga poses and stretches tailored for athletes."));
    //     lessonCollection.add(new PrivateLesson("7","Strength Training", "Introduction to weightlifting and bodyweight exercises."));
    //     lessonCollection.add(new PublicLesson("8","Cycling Basics", "Learn cycling techniques, safety, and endurance training."));
    //     lessonCollection.add(new PublicLesson("9","Volleyball", "Overview of volleyball basics, including serving, passing, and setting."));
    //     lessonCollection.add(new PrivateLesson("10","Martial Arts", "Learn fundamental martial arts moves and self-defense techniques."));
    // }

    // public String getAllLessonsDescriptions(){
    //     StringBuilder description = new StringBuilder("");
    //     for (Lesson lesson : lessonCollection) {
    //         description.append(lesson.toString()+ " \n");
    //     }
    //     return description.toString();
    // }

    public String getAllLocationsDescriptions(){
        StringBuilder description = new StringBuilder("");
        for (Location location : locationCollection) {
            description.append(location.toString()+ " \n");
        }
        return description.toString();
    }

    // public Lesson getLessonById(String id){
    //     Lesson lesson = null;
    //     for (Lesson lesson2 : lessonCollection) {
    //         if(lesson2.getID() == id){
    //             lesson = lesson2;
    //             break;
    //         }
    //     }
    //     return lesson;
    // }

    public Location getLocationById(String id){
        Location location = null;
        for (Location location2 : locationCollection) {
            if(location2.getID() == id){
                location = location2;
                break;
            }
        }
        return location;
    }

}
