package learn.register.domain;

import learn.register.models.Course;
import learn.register.data.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Fetch all courses
    public Result<List<Course>> findAllCourses() {
        Result<List<Course>> result = new Result<>();
        List<Course> courses = courseRepository.findAll();

        if (courses.isEmpty()) {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("No courses found.");
        } else {
            result.setType(ResultType.SUCCESS);
            result.setPayload(courses);
        }
        return result;
    }


    // Fetch a course by ID
    public Result<Course> findCourseById(UUID courseId) {
        Result<Course> result = new Result<>();
        Course course = courseRepository.findById(courseId);

        if (course == null) {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Course not found.");
        } else {
            result.setType(ResultType.SUCCESS);
            result.setPayload(course);
        }
        return result;
    }


    // Add a new course
    public int addCourse(Course course) {
        course.setCourseId(UUID.randomUUID()); // Generate UUID for new course
        return courseRepository.save(course);
    }

    // Update an existing course
    public int updateCourse(UUID courseId, Course course) {
        course.setCourseId(courseId); // Set the correct course ID
        return courseRepository.update(course);
    }

    // Delete a course by ID
    public int deleteCourseById(UUID courseId) {
        return courseRepository.deleteById(courseId);
    }
}