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
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    // Fetch a course by ID
    public Course findCourseById(UUID courseId) {
        return courseRepository.findById(courseId);
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