package learn.register.data;

import learn.register.models.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import learn.register.data.mappers.StudentMapper;

import java.util.List;

@Repository
public class StudentJdbcTemplateRepository implements StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Student> findAll() {
        final String sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, new StudentMapper());
    }

    @Override
    public Student findById(Long studentId) {
        final String sql = "SELECT * FROM student WHERE student_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{studentId}, new StudentMapper());
    }

    @Override
    public int save(Student student) {
        final String sql = "INSERT INTO student (first_name, last_name, email, phone, major, year, credits) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getMajor(),
                student.getYear(),
                student.getCredits());
    }

    @Override
    public int update(Student student) {
        final String sql = "UPDATE student SET first_name = ?, last_name = ?, email = ?, phone = ?, major = ?, year = ?, credits = ? WHERE student_id = ?";
        return jdbcTemplate.update(sql,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getMajor(),
                student.getYear(),
                student.getCredits(),
                student.getStudentId());
    }

    @Override
    public int deleteById(Long studentId) {
        final String sql = "DELETE FROM student WHERE student_id = ?";
        return jdbcTemplate.update(sql, studentId);
    }
}
