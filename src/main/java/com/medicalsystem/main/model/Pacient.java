package com.medicalsystem.main.model;

import java.util.Date;
import java.util.List;

public class Pacient {

    private String id;
    private String lastName;
    private String firstName;
    private String birthDate;
    private List<String> diseases;

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

    public String getBirthDate() { return birthDate; }

    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public List<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<String> diseases) {
        this.diseases = diseases;
    }

    public void addDisease(String disease) { this.diseases.add(disease);}
}
