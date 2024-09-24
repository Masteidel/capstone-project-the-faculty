package learn.register.data;

import learn.register.models.Enrollment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EnrollmentJdbcTemplateRepositoryTest {

    @Autowired
    EnrollmentJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findAll() {
        Enrollment enrollment1 = new Enrollment(null, "Active", 1L, 1L);
        Enrollment enrollment2 = new Enrollment(null, "Completed", 2L, 1L);
        repository.save(enrollment1);
        repository.save(enrollment2);

        List<Enrollment> enrollments = repository.findAll();
        assertNotNull(enrollments);

        // Assuming there are at least 2 enrollments after the save operations
        assertTrue(!enrollments.isEmpty());
    }

    @Test
    void findById() {
        Long enrollmentId = 1L; // Assuming this ID is valid in the test database
        Enrollment enrollment = new Enrollment(enrollmentId, "Active", 1L, 1L);
        repository.save(enrollment);

        Enrollment foundEnrollment = repository.findById(enrollmentId);

        assertNotNull(foundEnrollment);
        assertEquals(enrollmentId, foundEnrollment.getEnrollmentId());
        assertEquals("Active", foundEnrollment.getStatus());
    }

    @Test
    void save() {
        Enrollment enrollment = new Enrollment(null, "Active", 1L, 1L);
        int rowsAffected = repository.save(enrollment);
        assertEquals(1, rowsAffected); // One row should be inserted

        Enrollment actual = repository.findById(enrollment.getEnrollmentId());
        assertNotNull(actual);
        assertEquals(enrollment.getStatus(), actual.getStatus());
    }

    @Test
    void update() {
        Long enrollmentId = 1L;
        Enrollment enrollment = new Enrollment(enrollmentId, "Active", 1L, 1L);
        repository.save(enrollment);

        enrollment.setStatus("Completed");
        int rowsAffected = repository.update(enrollment);
        assertEquals(1, rowsAffected); // One row should be updated

        Enrollment updated = repository.findById(enrollment.getEnrollmentId());
        assertEquals("Completed", updated.getStatus());

        // Testing update on a non-existing enrollment
        enrollment.setEnrollmentId(999L); // Non-existing ID
        rowsAffected = repository.update(enrollment);
        assertEquals(0, rowsAffected); // No rows should be updated
    }

    @Test
    void deleteById() {
        Enrollment enrollment = new Enrollment(null, "Active", 1L, 1L);
        repository.save(enrollment);
        Long enrollmentId = enrollment.getEnrollmentId();

        int rowsAffected = repository.deleteById(enrollmentId);
        assertEquals(1, rowsAffected); // One row should be deleted

        Enrollment deletedEnrollment = repository.findById(enrollmentId);
        assertNull(deletedEnrollment); // The enrollment should not exist anymore
    }

    private Enrollment makeEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(1L); // Example ID, assuming it's auto-generated in the real database
        enrollment.setStatus("Active");
        enrollment.setStudentId(1L);
        enrollment.setSectionId(1L);
        return enrollment;
    }
}
