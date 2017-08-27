package uk.ac.anglia.student.hilda.studentadviser.model;

/**
 * Created by Ibok on 07/04/2017.
 */

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private String password;
    private boolean isequals = false;

    public User() {
    }

    public User(int id) {

        this.id = id;
    }

    public User(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public boolean isequals(int id, String password) {
        if (this.password == password) {
            this.isequals = true;
        }
        return isequals;
    }
}

