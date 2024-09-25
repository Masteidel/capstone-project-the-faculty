package learn.register.data;

import learn.register.models.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseJdbcTemplateRepositoryTest {

    @Autowired
    CourseJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findAll() {
        Course course1 = new Course(null, "Math 101", "Mathematics", 3);
        Course course2 = new Course(null, "History 101", "History", 3);
        repository.save(course1);
        repository.save(course2);

        List<Course> courses = repository.findAll();
        assertNotNull(courses);

        // Assuming there could be between 5 and 15 courses depending on the current state
        assertTrue(!courses.isEmpty());
    }

    @Test
    void findById() {
        Long courseId = 3L; // Assume this ID is assigned after save
        Course course = new Course(courseId, "Math 101", "Mathematics", 3);
        repository.save(course);

        Course foundCourse = repository.findById(courseId);

        assertNotNull(foundCourse);
        assertEquals(courseId, foundCourse.getCourseId());
        assertEquals("Math 101", foundCourse.getName());
    }

    @Test
    void save() {
        Course course = new Course(3L, "Math 101", "Mathematics", 3);
        int rowsAffected = repository.save(course);
        assertEquals(1, rowsAffected); // One row should be inserted

        Course actual = repository.findById(course.getCourseId());
        assertNotNull(actual);
        assertEquals(course.getCourseId(), actual.getCourseId());
        assertEquals(course.getName(), actual.getName());
    }

    @Test
    void update() {
        Long courseId = 1L;
        Course course = new Course(courseId, "Math 101", "Mathematics", 3);
        repository.save(course);

        course.setName("Math 201");
        course.setCredits(4);
        int rowsAffected = repository.update(course);
        assertEquals(1, rowsAffected); // 1 row should be updated

        Course updated = repository.findById(course.getCourseId());
        assertEquals(course.getName(), updated.getName());
        assertEquals(course.getCredits(), updated.getCredits());

        // Testing update on a non-existing course
        course.setCourseId(999L); // Use a non-existing Long ID
        rowsAffected = repository.update(course);
        assertEquals(0, rowsAffected); // No rows should be affected
    }


    private Course makeCourse() {
        Course course = new Course();
        course.setCourseId(1L); // Example ID, assuming it's auto-generated in the real database
        course.setName("Test Course");
        course.setSubject("Test Subject");
        course.setCredits(3);
        return course;
    }
}
