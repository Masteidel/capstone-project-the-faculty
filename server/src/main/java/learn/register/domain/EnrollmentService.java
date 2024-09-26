package learn.register.domain;

import learn.register.data.StudentRepository;
import learn.register.models.Enrollment;
import learn.register.data.EnrollmentRepository;
import learn.register.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final EmailService emailService;  // Inject EmailService

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             EmailService emailService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.emailService = emailService;
    }

    // Fetch all enrollments
    public List<Enrollment> findAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // Fetch an enrollment by ID
    public Enrollment findEnrollmentById(Long enrollmentId) {
        return enrollmentRepository.findById(enrollmentId);
    }

    // Add a new enrollment
    public Result<Enrollment> addEnrollment(Enrollment enrollment) {
        Result<Enrollment> result = validate(enrollment);
        if (!result.isSuccess()) {
            return result;
        }

        int saveResult = enrollmentRepository.save(enrollment);

        if (saveResult > 0) {
            result.setType(ResultType.SUCCESS);
            result.setPayload(enrollment);

            // After successful enrollment, send confirmation email
            Student student = studentRepository.findById(enrollment.getStudentId());
            if (student != null) {
                try {
                    sendConfirmationEmail(student);
                } catch (IOException e) {
                    result.setType(ResultType.ERROR);
                    result.setMessage("Enrollment succeeded, but failed to send confirmation email.");
                    return result;
                }
            }

        } else {
            result.setType(ResultType.ERROR);
            result.setMessage("Could not save the enrollment.");
        }

        return result;
    }


    // Send email confirmation to the student
    private void sendConfirmationEmail(Student student) throws IOException {
        String subject = "Course Enrollment Confirmation";
        String body = "Dear " + student.getFirstName() + ",\n\nYou have successfully enrolled in the course.";
        emailService.sendEmail(student.getEmail(), subject, body);
    }

    // Update an existing enrollment
    public Result<Enrollment> updateEnrollment(Long enrollmentId, Enrollment enrollment) {
        Result<Enrollment> result = validate(enrollment);
        if (!result.isSuccess()) {
            return result;
        }

        enrollment.setEnrollmentId(enrollmentId); // Set the correct ID
        int updateResult = enrollmentRepository.update(enrollment);

        if (updateResult > 0) {
            result.setType(ResultType.SUCCESS);
            result.setPayload(enrollment); // Set the updated enrollment as the payload
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Enrollment not found.");
        }

        return result;
    }

    // Delete an enrollment by ID
    public Result<Void> deleteEnrollmentById(Long enrollmentId) {
        Result<Void> result = new Result<>();
        int deleteResult = enrollmentRepository.deleteById(enrollmentId);

        if (deleteResult > 0) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Enrollment not found.");
        }

        return result;
    }

    private Result<Enrollment> validate(Enrollment enrollment) {
        Result<Enrollment> result = new Result<>();

        if (enrollment.getStudentId() == null) {
            result.setType(ResultType.INVALID);
            result.setMessage("Student ID is required.");
            return result;
        }

        if (enrollment.getSectionId() == null) {
            result.setType(ResultType.INVALID);
            result.setMessage("Section ID is required.");
            return result;
        }

        result.setType(ResultType.SUCCESS);
        return result;
    }
}