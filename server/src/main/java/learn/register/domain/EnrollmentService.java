package learn.register.domain;

import learn.register.models.Enrollment;
import learn.register.data.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
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
        } else {
            result.setType(ResultType.ERROR);
            result.setMessage("Could not save the enrollment.");
        }

        return result;
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