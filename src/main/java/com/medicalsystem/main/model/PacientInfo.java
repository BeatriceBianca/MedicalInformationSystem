package com.medicalsystem.main.model;

public class PacientInfo {

    private String id;
    private String lastName;
    private String firstName;

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

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof PacientInfo)) {
            return false;
        }

        PacientInfo user = (PacientInfo) o;

        return user.id.equals(id) &&
                user.lastName.equals(lastName) &&
                user.firstName.equals(firstName);
    }

//    //Idea from effective Java : Item 9
//    @Override
//    public int hashCode() {
//        int result = 17;
//        result = 31 * result + name.hashCode();
//        result = 31 * result + age;
//        result = 31 * result + passport.hashCode();
//        return result;
//    }
}
