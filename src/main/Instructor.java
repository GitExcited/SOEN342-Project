package main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Instructor {
    private String id;
    private String name;
    private String phoneNumber;
    private int age;
    private String password;
    private ArrayList <String> cities = new ArrayList<String>();

    // Constructor
    public Instructor(String name, String phoneNumber, int age, String password) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
        // CRUD operations for cities

    // Create: Add a city
    public void addCity(String city) {
        cities.add(city);
    }

    // Read: Get all cities
    public List<String> getCities() {
        return cities;
    }

    // Update: Update a city
    public void updateCity(int index, String newCity) {
        if (index >= 0 && index < cities.size()) {
            cities.set(index, newCity);
        } else {
            System.out.println("Invalid index");
        }
    }

    // Delete: Remove a city
    public void removeCity(int index) {
        if (index >= 0 && index < cities.size()) {
            cities.remove(index);
        } else {
            System.out.println("Invalid index");
        }
    }

    public String toString(){
        return "Id: "+ id+ " Name: "+ name + " phoneNumber: "+ phoneNumber;
    }
}
