package learn.register.data.mappers;

import learn.register.models.Enrollment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentMapper implements RowMapper<Enrollment> {

    @Override
    public Enrollment mapRow(ResultSet resultSet, int i) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(resultSet.getLong("enrollment_id"));
        enrollment.setStatus(resultSet.getString("status"));
        enrollment.setStudentId(resultSet.getLong("student_id"));
        enrollment.setSectionId(resultSet.getLong("section_id"));
        return enrollment;
    }
}
