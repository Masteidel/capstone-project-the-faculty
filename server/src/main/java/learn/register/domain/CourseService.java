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
    public Result<Course> addCourse(Course course) {
        Result<Course> result = validate(course);
        if (!result.isSuccess()) {
            return result;
        }

        course.setCourseId(UUID.randomUUID()); // Generate UUID for new course
        int saveResult = courseRepository.save(course);

        if (saveResult > 0) {
            result.setType(ResultType.SUCCESS);
            result.setPayload(course);
        } else {
            result.setType(ResultType.ERROR);
            result.setMessage("Could not save the course.");
        }
        return result;
    }

    // Update an existing course
    public Result<Course> updateCourse(UUID courseId, Course course) {
        Result<Course> result = validate(course);
        if (!result.isSuccess()) {
            return result;
        }

        course.setCourseId(courseId); // Set the correct course ID
        int updateResult = courseRepository.update(course);

        if (updateResult > 0) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Course not found.");
        }

        return result;
    }

    // Delete a course by ID
    public Result<Void> deleteCourseById(UUID courseId) {
        Result<Void> result = new Result<>();
        int deleteResult = courseRepository.deleteById(courseId);

        if (deleteResult > 0) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Course not found.");
        }

        return result;
    }

    private Result<Course> validate(Course course) {
        Result<Course> result = new Result<>();

        if (course.getName() == null || course.getName().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("Course name is required.");
            return result;
        }

        if (course.getSubject() == null || course.getSubject().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("Course subject is required.");
            return result;
        }

        if (course.getCredits() <= 0) {
            result.setType(ResultType.INVALID);
            result.setMessage("Credits must be greater than zero.");
            return result;
        }

        result.setType(ResultType.SUCCESS);
        return result;
    }
}