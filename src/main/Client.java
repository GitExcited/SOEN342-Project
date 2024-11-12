package main;

import java.util.UUID;

public class Client {
    private String id;
    private String name;
    private String phoneNumber;
    private int age;
    private String password;

    // Constructor
    public Client(String name, String phoneNumber, int age, String password) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.password = password;
    }

    // Getters and setters for name and email
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

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return "Id: "+ id+ " Name: "+ name + " phoneNumber: "+ phoneNumber + " Age: "+ age;
    }

        /**
     * Helper method to turn Client  into paramters that can be passed to its TDG 
     * @param offering
     * @return Object array with each of its attributes in order
     */
    public Object[] toParams() {
        return new Object[] {
            this.id,
            this.name,
            this.phoneNumber,
            this.age,
            this.password
        };
    }
}
