package learn.register.data;

import learn.register.models.Enrollment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EnrollmentJdbcTemplateRepositoryTest {

    @Autowired
    EnrollmentJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // Optional cleanup if needed: you can truncate or reset tables here
        jdbcTemplate.update("DELETE FROM enrollment");
    }

    @Test
    void shouldFindAll() {
        // Insert a couple of enrollments for testing
        jdbcTemplate.update("INSERT INTO enrollment (status, student_id, section_id) VALUES (?, ?, ?)",
                "Enrolled", 1L, 1L);
        jdbcTemplate.update("INSERT INTO enrollment (status, student_id, section_id) VALUES (?, ?, ?)",
                "Waitlisted", 2L, 2L);

        List<Enrollment> enrollments = repository.findAll();
        assertNotNull(enrollments);
        assertEquals(2, enrollments.size());
    }

    @Test
    void shouldFindById() {
        // Insert an enrollment and get the ID
        jdbcTemplate.update("INSERT INTO enrollment (status, student_id, section_id) VALUES (?, ?, ?)",
                "Enrolled", 1L, 1L);
        Long enrollmentId = jdbcTemplate.queryForObject("SELECT MAX(enrollment_id) FROM enrollment", Long.class);

        Enrollment enrollment = repository.findById(enrollmentId);
        assertNotNull(enrollment);
        assertEquals(enrollmentId, enrollment.getEnrollmentId());
        assertEquals("Enrolled", enrollment.getStatus());
    }

    @Test
    void shouldSave() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus("Enrolled");
        enrollment.setStudentId(1L); // Use valid student_id
        enrollment.setSectionId(1L); // Use valid section_id

        int rowsAffected = repository.save(enrollment);
        assertEquals(1, rowsAffected); // Should insert 1 row

        // Verify insertion
        Long newEnrollmentId = jdbcTemplate.queryForObject("SELECT MAX(enrollment_id) FROM enrollment", Long.class);
        Enrollment insertedEnrollment = repository.findById(newEnrollmentId);
        assertEquals("Enrolled", insertedEnrollment.getStatus());
        assertEquals(1L, insertedEnrollment.getStudentId());
        assertEquals(1L, insertedEnrollment.getSectionId());
    }

    @Test
    void shouldUpdate() {
        // Insert an enrollment and get the ID
        jdbcTemplate.update("INSERT INTO enrollment (status, student_id, section_id) VALUES (?, ?, ?)",
                "Enrolled", 1L, 1L);
        Long enrollmentId = jdbcTemplate.queryForObject("SELECT MAX(enrollment_id) FROM enrollment", Long.class);

        Enrollment enrollment = repository.findById(enrollmentId);
        enrollment.setStatus("Updated Status");

        int rowsAffected = repository.update(enrollment);
        assertEquals(1, rowsAffected);

        // Verify update
        Enrollment updatedEnrollment = repository.findById(enrollmentId);
        assertEquals("Updated Status", updatedEnrollment.getStatus());
    }

    @Test
    void shouldDeleteById() {
        // Insert an enrollment and get the ID
        jdbcTemplate.update("INSERT INTO enrollment (status, student_id, section_id) VALUES (?, ?, ?)",
                "Enrolled", 1L, 1L);
        Long enrollmentId = jdbcTemplate.queryForObject("SELECT MAX(enrollment_id) FROM enrollment", Long.class);

        int rowsAffected = repository.deleteById(enrollmentId);
        assertEquals(1, rowsAffected);

        // Verify deletion
        assertThrows(Exception.class, () -> repository.findById(enrollmentId));
    }
}
