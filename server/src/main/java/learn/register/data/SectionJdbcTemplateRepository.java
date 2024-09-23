package learn.register.data;

import learn.register.models.Section;
import learn.register.data.mappers.SectionMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SectionJdbcTemplateRepository implements SectionRepository {

    private final JdbcTemplate jdbcTemplate;

    public SectionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Section> findAll() {
        final String sql = "SELECT * FROM section";
        return jdbcTemplate.query(sql, new SectionMapper());
    }

    @Override
    public Section findById(Long sectionId) {
        final String sql = "SELECT * FROM section WHERE section_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{sectionId}, new SectionMapper());
    }

    @Override
    public int save(Section section) {
        final String sql = "INSERT INTO section (abbreviation, student_cap, course_id, professor_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                section.getAbbreviation(),
                section.getStudentCap(),
                section.getCourseId(),
                section.getProfessorId());
    }

    @Override
    public int update(Section section) {
        final String sql = "UPDATE section SET abbreviation = ?, student_cap = ?, course_id = ?, professor_id = ? WHERE section_id = ?";
        return jdbcTemplate.update(sql,
                section.getAbbreviation(),
                section.getStudentCap(),
                section.getCourseId(),
                section.getProfessorId(),
                section.getSectionId());
    }

    @Override
    public int deleteById(Long sectionId) {
        final String sql = "DELETE FROM section WHERE section_id = ?";
        return jdbcTemplate.update(sql, sectionId);
    }
}
