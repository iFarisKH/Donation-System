package io.github.ifariskh.donationsystem.core;

public class EndUser extends User {

    private String gender;
    private String socialStatus;
    private double salary;
    public static String KYC = "No";
    public static String ID = null;

    public EndUser(String id, String gender, String socialStatus, double salary, String dob) {
        this.gender = gender;
        this.socialStatus = socialStatus;
        this.salary = salary;
        this.setDob(dob);
        this.setId(id);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSocialStatus() {
        return socialStatus;
    }

    public void setSocialStatus(String socialStatus) {
        this.socialStatus = socialStatus;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        salary = salary;
    }
}
