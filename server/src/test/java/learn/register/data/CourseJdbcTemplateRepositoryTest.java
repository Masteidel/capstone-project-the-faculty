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

    void findAll() {
        Course course1 = new Course(null, "Math 101", "Mathematics", 3);
        Course course2 = new Course(null, "History 101", "History", 3);
        repository.save(course1);
        repository.save(course2);


        List<Course> courses = repository.findAll();
        assertNotNull(courses);

        // Assuming there could be between 5 and 15 courses depending on the current state
        assertTrue(courses.size() >= 5 && courses.size() <= 15);
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
        course.setCourseId(UUID.randomUUID());
        rowsAffected = repository.update(course);
        assertEquals(0, rowsAffected); // No rows should be affected
    }

    @Test

    void deleteById() {
        Long courseId = 1L;
        Course course = new Course(courseId, "Math 101", "Mathematics", 3);
        repository.save(course);
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
