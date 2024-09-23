package learn.register.services;

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
    public int addEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    // Update an existing enrollment
    public int updateEnrollment(Long enrollmentId, Enrollment enrollment) {
        enrollment.setEnrollmentId(enrollmentId); // Ensure the correct ID is set
        return enrollmentRepository.update(enrollment);
    }

    // Delete an enrollment by ID
    public int deleteEnrollmentById(Long enrollmentId) {
        return enrollmentRepository.deleteById(enrollmentId);
    }
}
