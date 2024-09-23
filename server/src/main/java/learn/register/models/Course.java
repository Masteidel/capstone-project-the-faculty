package learn.register.models;

import java.util.UUID;

public class Course {
    private UUID courseId;
    private String name;
    private String subject;
    private int credits;

    // Constructors
    public Course() {}

    public Course(UUID courseId, String name, String subject, int credits) {
        this.courseId = courseId;
        this.name = name;
        this.subject = subject;
        this.credits = credits;
    }

    // Getters and Setters
    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
