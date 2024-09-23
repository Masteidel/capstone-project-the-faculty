package learn.register.data.mappers;

import learn.register.models.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgencyAgentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {


        Student agencyAgent;
        return agencyAgent;
    }
}
