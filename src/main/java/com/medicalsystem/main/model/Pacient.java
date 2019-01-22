package com.medicalsystem.main.model;

import java.util.Date;
import java.util.List;

public class Pacient {

    private String id;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private List<String> disease;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() { return birthDate; }

    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public List<String> getDisease() {
        return disease;
    }

    public void setDisease(List<String> disease) {
        this.disease = disease;
    }
}
