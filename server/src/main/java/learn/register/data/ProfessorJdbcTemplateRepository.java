package learn.register.data;

import learn.register.data.mappers.ProfessorMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProfessorJdbcTemplateRepository implements ProfessorRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProfessorJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Professor> findAll() {
        final String sql = "SELECT professor_id, first_name, last_name, email, phone FROM professor";
        return jdbcTemplate.query(sql, new ProfessorMapper());
    }

    @Override
    @Transactional
    public Professor findById(int professorId) {
        final String sql = "SELECT professor_id, first_name, last_name, email, phone "
                + "FROM professor WHERE professor_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ProfessorMapper(), professorId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Professor add(Professor professor) {
        final String sql = "INSERT INTO professor (first_name, last_name, email, phone) "
                + "VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, professor.getFirstName());
            ps.setString(2, professor.getLastName());
            ps.setString(3, professor.email());
            ps.setString(4, professor.phone());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        professor.setProfessorId(keyHolder.getKey().intValue());
        return professor;
    }

    @Override
    public boolean update(Professor professor) {
        final String sql = "UPDATE professor SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "email = ?, "
                + "phone = ? "
                + "WHERE professor_id = ?";
        return jdbcTemplate.update(sql,
                professor.getFirstName(),
                professor.getLastName(),
                professor.getEmail(),
                professor.getPhone(),
                professor.getProfessorId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int professorId) {
        final String sql = "DELETE FROM professor WHERE professor_id = ?";
        return jdbcTemplate.update(sql, professorId) > 0;
    }

    private static final class ProfessorMapper implements RowMapper<Professor> {

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
}
