package learn.register.data;

import learn.register.models.Enrollment;
import learn.register.data.mappers.EnrollmentMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnrollmentJdbcTemplateRepository implements EnrollmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public EnrollmentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Enrollment> findAll() {
        final String sql = "SELECT * FROM enrollment";
        return jdbcTemplate.query(sql, new EnrollmentMapper());
    }

    @Override
    public Enrollment findById(Long enrollmentId) {
        final String sql = "SELECT * FROM enrollment WHERE enrollment_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{enrollmentId}, new EnrollmentMapper());
    }

    @Override
    public int save(Enrollment enrollment) {
        final String sql = "INSERT INTO enrollment (status, student_id, section_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                enrollment.getStatus(),
                enrollment.getStudentId(),
                enrollment.getSectionId());
    }

    @Override
    public int update(Enrollment enrollment) {
        final String sql = "UPDATE enrollment SET status = ?, student_id = ?, section_id = ? WHERE enrollment_id = ?";
        return jdbcTemplate.update(sql,
                enrollment.getStatus(),
                enrollment.getStudentId(),
                enrollment.getSectionId(),
                enrollment.getEnrollmentId());
    }

    @Override
    public int deleteById(Long enrollmentId) {
        final String sql = "DELETE FROM enrollment WHERE enrollment_id = ?";
        return jdbcTemplate.update(sql, enrollmentId);
    }
}
