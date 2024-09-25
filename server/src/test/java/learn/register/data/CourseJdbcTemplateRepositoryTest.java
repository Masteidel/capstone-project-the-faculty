package learn.register.data;

import learn.register.models.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CourseJdbcTemplateRepositoryTest {

    @Autowired
    CourseJdbcTemplateRepository repository;

    Course course;

    @BeforeEach
    void setup() {
        course = new Course();
        course.setName("Math 101");
        course.setSubject("Mathematics");
        course.setCredits(3);
    }

    @Test
    void findAll() {
        List<Course> courses = repository.findAll();
        assertNotNull(courses);
        assertTrue(courses.size() > 0);
    }

    @Test
    void findById() {
        Course course = repository.findById(1L);
        assertNotNull(course);
        assertEquals("Math 101", course.getName());
    }

    @Test
    void save() {
        int saved = repository.save(course);
        assertTrue(saved > 0);
    }

    @Test
    void update() {
        course.setCourseId(1L); // Set an existing ID to update
        course.setName("Updated Name");

        int result = repository.update(course);
        assertEquals(1, result); // Verifying one row is updated

        Course updatedCourse = repository.findById(1L);
        assertEquals("Updated Name", updatedCourse.getName());
    }

    @Test
    void deleteById() {
        int result = repository.deleteById(1L); // Assuming there is a course with ID 1
        assertEquals(1, result);

        Course deletedCourse = repository.findById(1L);
        assertNull(deletedCourse);
    }
}