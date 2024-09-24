package learn.register.data;

import learn.register.models.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
class CourseJdbcTemplateRepositoryTest {

    @Autowired
    CourseJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set(); // Reset the database to a known good state
    }

    @Test
    void findAll() {
        Course course1 = new Course(null, "Math 101", "Mathematics", 3);
        Course course2 = new Course(null, "History 101", "History", 3);
        repository.save(course1);
        repository.save(course2);

        List<Course> courses = repository.findAll();

        assertEquals(2, courses.size());
        assertTrue(courses.contains(course1));
        assertTrue(courses.contains(course2));
    }

    @Test
    void findById() {
        Long courseId = 1L; // Assume this ID is assigned after save
        Course course = new Course(courseId, "Math 101", "Mathematics", 3);
        repository.save(course);

        Course foundCourse = repository.findById(courseId);

        assertNotNull(foundCourse);
        assertEquals(courseId, foundCourse.getCourseId());
        assertEquals("Math 101", foundCourse.getName());
    }

    @Test
    void save() {
        Course course = new Course(null, "Math 101", "Mathematics", 3);

        int rowsAffected = repository.save(course);

        assertEquals(1, rowsAffected);
        assertNotNull(course.getCourseId());
    }

    @Test
    void update() {
        Long courseId = 1L;
        Course course = new Course(courseId, "Math 101", "Mathematics", 3);
        repository.save(course);

        course.setName("Math 201");
        course.setCredits(4);

        int rowsAffected = repository.update(course);

        assertEquals(1, rowsAffected);
        Course updatedCourse = repository.findById(courseId);
        assertEquals("Math 201", updatedCourse.getName());
        assertEquals(4, updatedCourse.getCredits());
    }

    @Test
    void deleteById() {
        Long courseId = 1L;
        Course course = new Course(courseId, "Math 101", "Mathematics", 3);
        repository.save(course);

        int rowsAffected = repository.deleteById(courseId);

        assertEquals(1, rowsAffected);
        assertNull(repository.findById(courseId));
    }
}