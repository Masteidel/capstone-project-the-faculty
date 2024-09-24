package learn.register.data;

import learn.register.models.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import learn.register.data.mappers.CourseMapper;

import java.util.List;
import java.util.UUID;

@Repository
public class CourseJdbcTemplateRepository implements CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public CourseJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> findAll() {
        final String sql = "SELECT * FROM course";
        return jdbcTemplate.query(sql, new CourseMapper());
    }

    @Override
    public Course findById(Long courseId) { // Change UUID to Long
        final String sql = "SELECT * FROM course WHERE course_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{courseId}, new CourseMapper());
    }

    @Override
    public int save(Course course) {
        // Assuming course_id is auto-generated, do not include it in the insert statement
        final String sql = "INSERT INTO course (name, subject, credits) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                course.getName(),
                course.getSubject(),
                course.getCredits());
    }


    @Override
    public int update(Course course) {
        final String sql = "UPDATE course SET name = ?, subject = ?, credits = ? WHERE course_id = ?";
        return jdbcTemplate.update(sql,
                course.getName(),
                course.getSubject(),
                course.getCredits(),
                course.getCourseId().toString());
    }

    @Override
    public int deleteById(Long courseId) {
        final String sql = "DELETE FROM course WHERE course_id = ?";
        return jdbcTemplate.update(sql, courseId.toString());
    }
}