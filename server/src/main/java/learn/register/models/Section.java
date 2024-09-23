package learn.register.models;

public class Section {

    private Long sectionId;
    private String abbreviation;
    private int studentCap;
    private Long courseId;      // Foreign key referencing the course
    private Long professorId;   // Foreign key referencing the professor

    // Constructors
    public Section() {}

    public Section(Long sectionId, String abbreviation, int studentCap, Long courseId, Long professorId) {
        this.sectionId = sectionId;
        this.abbreviation = abbreviation;
        this.studentCap = studentCap;
        this.courseId = courseId;
        this.professorId = professorId;
    }

    // Getters and Setters
    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }
}