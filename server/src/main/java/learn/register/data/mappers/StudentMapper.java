package learn.register.data.mappers;

import learn.register.models.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();

        student.setStudentId(resultSet.getLong("student_id"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        student.setEmail(resultSet.getString("email"));
        student.setPhone(resultSet.getString("phone"));
        student.setMajor(resultSet.getString("major"));
        student.setYear(resultSet.getInt("year"));
        student.setCredits(resultSet.getInt("credits"));

        return student;
    }
}