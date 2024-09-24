package learn.register.data;

import learn.register.models.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseJdbcTemplateRepositoryTest {

    final static UUID NEXT_ID = UUID.randomUUID(); // You can use a UUID generator for the next course ID

    @Autowired
    CourseJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Course> courses = repository.findAll();
        assertNotNull(courses);

        // Assuming there could be between 5 and 15 courses depending on the current state
        assertTrue(courses.size() >= 5 && courses.size() <= 15);
    }

    @Test
    void shouldFindSpecificCourse() {
        UUID courseId = UUID.fromString("some-existing-course-id"); // Replace with an actual course ID from the known good state
        Course course = repository.findById(courseId);
        assertEquals(courseId, course.getCourseId());
        assertEquals("Introduction to Programming", course.getName()); // Adjust according to your expected data
        assertEquals(4, course.getCredits()); // Assuming the credits are 4
    }

    @Test
    void shouldAdd() {
        Course course = makeCourse();
        int rowsAffected = repository.save(course);
        assertEquals(1, rowsAffected); // One row should be inserted

        Course actual = repository.findById(course.getCourseId());
        assertNotNull(actual);
        assertEquals(course.getCourseId(), actual.getCourseId());
        assertEquals(course.getName(), actual.getName());
    }

    @Test
    void shouldUpdate() {
        Course course = makeCourse();
        course.setCourseId(UUID.fromString("existing-course-id")); // Replace with actual ID from known good state
        int rowsAffected = repository.update(course);
        assertEquals(1, rowsAffected); // 1 row should be updated

        Course updated = repository.findById(course.getCourseId());
        assertEquals(course.getName(), updated.getName());
        assertEquals(course.getCredits(), updated.getCredits());

        // Testing update on a non-existing course
        course.setCourseId(UUID.randomUUID());
        rowsAffected = repository.update(course);
        assertEquals(0, rowsAffected); // No rows should be affected
    }

    @Test
    void shouldDelete() {
        // Use a known UUID from your known good state
        UUID courseId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

        int rowsAffected = repository.deleteById(courseId);
        assertEquals(1, rowsAffected); // One row should be deleted

        // Check that the course no longer exists
        Course course = repository.findById(courseId);
        assertNull(course);
    }

    private Course makeCourse() {
        Course course = new Course();
        course.setCourseId(NEXT_ID);
        course.setName("Test Course");
        course.setSubject("Test Subject");
        course.setCredits(3);
        return course;
    }
}
