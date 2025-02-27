package learn.register.data;

import learn.register.models.Course;

import java.util.List;
import java.util.UUID;

public interface CourseRepository {
    List<Course> findAll();
    Course findById(UUID courseId);
    int save(Course course);
    int update(Course course);
    int deleteById(UUID courseId);
}