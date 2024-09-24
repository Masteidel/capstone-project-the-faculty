package learn.register.data.mappers;

import learn.register.models.Section;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionMapper implements RowMapper<Section> {

    @Override
    public Section mapRow(ResultSet resultSet, int i) throws SQLException {
        Section section = new Section();
        section.setSectionId(resultSet.getString("section_id"));  // Map UUID as String
        section.setAbbreviation(resultSet.getString("abbreviation"));
        section.setStudentCap(resultSet.getInt("student_cap"));
        section.setCourseId(resultSet.getString("course_id"));    // Map UUID as String
        section.setProfessorId(resultSet.getString("professor_id")); // Map UUID as String
        return section;
    }
}