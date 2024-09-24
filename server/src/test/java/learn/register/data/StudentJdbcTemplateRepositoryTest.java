package learn.register.data;

import learn.register.models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class StudentJdbcTemplateRepositoryTest {

    @Autowired
    StudentJdbcTemplateRepository repository;

    Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setPhone("123-456-7890");
        student.setMajor("Computer Science");
        student.setYear("Junior");
        student.setCredits(15);
    }

    @Test
    void shouldFindAll() {
        List<Student> students = repository.findAll();
        assertNotNull(students);
        assertTrue(students.size() > 0);
    }

    @Test
    void shouldFindById() {
        Student student = repository.findById(1L);
        assertNotNull(student);
        assertEquals("John", student.getFirstName());
    }

    @Test
    void shouldSaveStudent() {
        int saved = repository.save(student);
        assertTrue(saved > 0); // or assertNotNull(saved.getStudentId()) if you modify save() to return the Student
    }

    @Test
    void shouldUpdateStudent() {
        student.setStudentId(1L); // Set an existing ID to update
        student.setFirstName("Updated Name");

        int result = repository.update(student);
        assertEquals(1, result); // Verifying one row is updated

        Student updatedStudent = repository.findById(1L);
        assertEquals("Updated Name", updatedStudent.getFirstName());
    }

    @Test
    void shouldDeleteById() {
        int result = repository.deleteById(1L); // Assuming there is a student with ID 1
        assertEquals(1, result);

        Student deletedStudent = repository.findById(1L);
        assertNull(deletedStudent);
    }
}
