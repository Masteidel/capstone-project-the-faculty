package learn.register.data;

import learn.register.models.Enrollment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EnrollmentJdbcTemplateRepositoryTest {

    @Autowired
    EnrollmentJdbcTemplateRepository repository;

    Enrollment enrollment;

    @BeforeEach
    void setUp() {
        enrollment = new Enrollment();
        enrollment.setStatus("Enrolled");
        enrollment.setStudentId(1L);
        enrollment.setSectionId(1L);
    }

    @Test
    void shouldFindAll() {
        List<Enrollment> enrollments = repository.findAll();
        assertNotNull(enrollments);
        assertTrue(enrollments.size() > 0);
    }

    @Test
    void shouldFindById() {
        Enrollment enrollment = repository.findById(1L);
        assertNotNull(enrollment);
        assertEquals("Enrolled", enrollment.getStatus());
    }

    @Test
    void shouldSaveEnrollment() {
        int saved = repository.save(enrollment);
        assertTrue(saved > 0); // or assertNotNull(saved.getEnrollmentId()) if you modify save() to return the Enrollment
    }

    @Test
    void shouldUpdateEnrollment() {
        enrollment.setEnrollmentId(1L); // Set an existing ID to update
        enrollment.setStatus("Waitlisted");

        int result = repository.update(enrollment);
        assertEquals(1, result); // Verifying one row is updated

        Enrollment updatedEnrollment = repository.findById(1L);
        assertEquals("Waitlisted", updatedEnrollment.getStatus());
    }

    @Test
    void shouldDeleteById() {
        int result = repository.deleteById(1L); // Assuming there is an enrollment with ID 1
        assertEquals(1, result);

        Enrollment deletedEnrollment = repository.findById(1L);
        assertNull(deletedEnrollment);
    }
}
