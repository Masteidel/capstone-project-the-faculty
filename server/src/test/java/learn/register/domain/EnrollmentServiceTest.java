package learn.register.domain;

import learn.register.data.EnrollmentRepository;
import learn.register.models.Enrollment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EnrollmentServiceTest {

    @Autowired
    EnrollmentService service;

    @MockBean
    EnrollmentRepository enrollmentRepository;

    @Test
    void shouldAddEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(1L);
        enrollment.setSectionId(2L);
        enrollment.setStatus("Enrolled");

        when(enrollmentRepository.save(enrollment)).thenReturn(1);

        Result<Enrollment> actual = service.addEnrollment(enrollment);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals("Enrolled", actual.getPayload().getStatus());
    }

    @Test
    void shouldNotAddInvalidEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(null); // Invalid enrollment with missing student ID

        Result<Enrollment> actual = service.addEnrollment(enrollment);
        assertEquals(ResultType.INVALID, actual.getType());

        enrollment.setStudentId(1L);
        enrollment.setSectionId(null); // Invalid enrollment with missing section ID
        actual = service.addEnrollment(enrollment);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdateEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(1L);
        enrollment.setSectionId(2L);
        enrollment.setStatus("Updated Status");

        when(enrollmentRepository.update(enrollment)).thenReturn(1);

        Result<Enrollment> actual = service.updateEnrollment(1L, enrollment);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals("Updated Status", actual.getPayload().getStatus());
    }

    @Test
    void shouldNotUpdateMissingEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(1L);
        enrollment.setSectionId(2L);
        enrollment.setStatus("Missing Status");

        when(enrollmentRepository.update(enrollment)).thenReturn(0);

        Result<Enrollment> actual = service.updateEnrollment(1L, enrollment);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldDeleteEnrollment() {
        Long enrollmentId = 1L;

        when(enrollmentRepository.deleteById(enrollmentId)).thenReturn(1);

        Result<Void> actual = service.deleteEnrollmentById(enrollmentId);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotDeleteMissingEnrollment() {
        Long enrollmentId = 1L;

        when(enrollmentRepository.deleteById(enrollmentId)).thenReturn(0);

        Result<Void> actual = service.deleteEnrollmentById(enrollmentId);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }
}
