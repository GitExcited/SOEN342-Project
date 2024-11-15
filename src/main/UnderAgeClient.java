package main;

public class UnderAgeClient extends Client {
    // Constructor
    public UnderAgeClient(String name, String email,int age, String password, String guardianId) {
        super(name, email,age , password);
        this.guardianId = guardianId;
    }

    // Getter and setter for guardian
    public String getGuardian() {
        return guardianId;
    }

    public void setGuardian(String guardian) {
        this.guardianId = guardian;
    }
    @Override
    public String toString() {
        return "UnderAgeClient{" +
                "id='" + getID() + '\'' +
                ", name='" + getName() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", age=" + getAge() +
                ", password='" + getPassword() + '\'' +
                ", guardianId='" + guardianId + '\'' +
                '}';
    }
}