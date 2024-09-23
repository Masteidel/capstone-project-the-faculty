package learn.register.models;

public class Enrollment {
    private Long enrollmentId;
    private String status;     // E.g., Enrolled, Waitlisted
    private Long studentId;    // Foreign key referencing student
    private Long sectionId;    // Foreign key referencing section

    // Constructors
    public Enrollment() {}

    public Enrollment(Long enrollmentId, String status, Long studentId, Long sectionId) {
        this.enrollmentId = enrollmentId;
        this.status = status;
        this.studentId = studentId;
        this.sectionId = sectionId;
    }

    // Getters and Setters
    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
}
