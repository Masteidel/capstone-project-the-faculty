package learn.register.domain;

import learn.register.data.StudentRepository;
import learn.register.models.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class StudentServiceTest {

    @Autowired
    StudentService service;

    @MockBean
    StudentRepository studentRepository;

    @Test
    void shouldAddStudent() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setPhone("123-456-7890");
        student.setMajor("Computer Science");
        student.setYear(2023);
        student.setCredits(30);

        when(studentRepository.save(student)).thenReturn(1);

        Result<Student> actual = service.saveStudent(student);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(student, actual.getPayload());
    }

    @Test
    void shouldNotAddInvalidStudent() {
        Student student = new Student();
        student.setFirstName(null); // Invalid student with missing first name

        Result<Student> actual = service.saveStudent(student);
        assertEquals(ResultType.INVALID, actual.getType());

        student.setFirstName("John");
        student.setYear(0); // Invalid student with year <= 0
        actual = service.saveStudent(student);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdateStudent() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setPhone("123-456-7890");
        student.setMajor("Computer Science");
        student.setYear(2023);
        student.setCredits(30);

        when(studentRepository.update(student)).thenReturn(1);

        Result<Student> actual = service.updateStudent(student);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissingStudent() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setPhone("123-456-7890");
        student.setMajor("Computer Science");
        student.setYear(2023);
        student.setCredits(30);

        when(studentRepository.update(student)).thenReturn(0);

        Result<Student> actual = service.updateStudent(student);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldDeleteStudent() {
        Long studentId = 1L;

        when(studentRepository.deleteById(studentId)).thenReturn(1);

        Result<Void> actual = service.deleteStudent(studentId);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotDeleteMissingStudent() {
        Long studentId = 1L;

        when(studentRepository.deleteById(studentId)).thenReturn(0);

        Result<Void> actual = service.deleteStudent(studentId);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }
}
