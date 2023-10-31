package model;

import java.util.List;

public class ResumeModel {
    private String name;
    private String surname;
    private int yearOfBirth;
    private List<String> skills;

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public List<String> getSkills() {
        return skills;
    }
}
