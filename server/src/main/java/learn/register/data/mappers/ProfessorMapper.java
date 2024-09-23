package learn.register.data.mappers;

import learn.register.models.Professor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorMapper implements RowMapper<Professor> {

    @Override
    public Professor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Professor professor = new Professor();
        professor.setProfessorId(rs.getInt("professor_id"));
        professor.setFirstName(rs.getString("first_name"));
        professor.setLastName(rs.getString("last_name"));
        professor.setEmail(rs.getString("email"));
        professor.setPhone(rs.getString("phone"));
        return professor;
    }
}