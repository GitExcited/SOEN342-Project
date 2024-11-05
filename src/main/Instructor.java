package main;

import java.util.UUID;

public class Instructor {
    private String id;
    private String name;
    private String phoneNumber;
    private int age;
    private String password;

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

    public String toString(){
        return "Name: "+ name + " phoneNumber: "+ phoneNumber;
    }
}
