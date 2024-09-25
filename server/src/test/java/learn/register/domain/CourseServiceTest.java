package learn.register.domain;

import learn.register.data.CourseRepository;
import learn.register.domain.CourseService;
import learn.register.domain.ResultType;
import learn.register.domain.Result;
import learn.register.models.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CourseServiceTest {

    @Autowired
    CourseService service;

    @MockBean
    CourseRepository courseRepository;

    @Test
    void shouldAddCourse() {
        Course course = new Course();
        course.setName("Test Course");
        course.setSubject("Math");
        course.setCredits(3);

        Course mockOut = new Course();
        mockOut.setCourseId(1L);
        mockOut.setName("Test Course");
        mockOut.setSubject("Math");
        mockOut.setCredits(3);

        when(courseRepository.save(course)).thenReturn(1);

        Result<Course> actual = service.addCourse(course);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotAddInvalidCourse() {
        Course course = new Course();
        course.setName(null); // Invalid course with missing name

        Result<Course> actual = service.addCourse(course);
        assertEquals(ResultType.INVALID, actual.getType());

        course.setName("Test Course");
        course.setCredits(3);
        course.setSubject("  "); // Invalid course with empty subject
        actual = service.addCourse(course);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdateCourse() {
        Course course = new Course();
        course.setName("Updated Course");
        course.setSubject("Science");
        course.setCredits(4);

        when(courseRepository.update(course)).thenReturn(1);

        Result<Course> actual = service.updateCourse(course.getCourseId(), course);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissingCourse() {
        Course course = new Course();
        course.setName("Missing Course");
        course.setSubject("Science");
        course.setCredits(4);

        when(courseRepository.update(course)).thenReturn(0);

        Result<Course> actual = service.updateCourse(course.getCourseId(), course);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldNotUpdateInvalidCourse() {
        Course course = new Course();
        course.setName(null); // Invalid course with missing name

        Result<Course> actual = service.updateCourse(course.getCourseId(), course);
        assertEquals(ResultType.INVALID, actual.getType());

        course.setName("Test Course");
        course.setCredits(3);
        course.setSubject("  "); // Invalid course with empty subject
        actual = service.updateCourse(course.getCourseId(), course);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldDeleteCourse() {
        Long courseId = 1L;

        when(courseRepository.deleteById(courseId)).thenReturn(1);

        Result<Void> actual = service.deleteCourseById(courseId);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotDeleteMissingCourse() {
        Long courseId = 1L;

        when(courseRepository.deleteById(courseId)).thenReturn(0);

        Result<Void> actual = service.deleteCourseById(courseId);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }
}
