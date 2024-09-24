package learn.register.models;

public class Section {

    private String sectionId;  // UUID stored as a String
    private String abbreviation;
    private int studentCap;
    private String courseId;   // UUID stored as a String
    private String professorId; // UUID stored as a String

    // Constructors
    public Section() {
    }

    public Section(String sectionId, String abbreviation, int studentCap, String courseId, String professorId) {
        this.sectionId = sectionId;
        this.abbreviation = abbreviation;
        this.studentCap = studentCap;
        this.courseId = courseId;
        this.professorId = professorId;
    }

    // Getters and Setters
    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getStudentCap() {
        return studentCap;
    }

    public void setStudentCap(int studentCap) {
        this.studentCap = studentCap;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }
}