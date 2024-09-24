package learn.register.data.mappers;

import learn.register.models.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CourseMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet resultSet, int i) throws SQLException {
        Course course = new Course();
        course.setCourseId(UUID.fromString(resultSet.getString("course_id")));
        course.setName(resultSet.getString("name"));
        course.setSubject(resultSet.getString("subject"));
        course.setCredits(resultSet.getInt("credits"));
        return course;
    }
}
