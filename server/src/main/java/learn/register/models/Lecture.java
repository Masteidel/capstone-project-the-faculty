package learn.register.models;

import java.time.LocalTime;

public class Lecture {

    private Long lectureId;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private int duration;
    private Long sectionId;

    public Lecture() {
    }

    public Lecture(Long lectureId, String day, LocalTime startTime, LocalTime endTime, int duration, Long sectionId) {
        this.lectureId = lectureId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.sectionId = sectionId;
    }

    // Getters and Setters

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
}