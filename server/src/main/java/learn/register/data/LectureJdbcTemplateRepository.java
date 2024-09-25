package learn.register.data;

import learn.register.data.mappers.EnrollmentMapper;
import learn.register.data.mappers.LectureMapper;
import learn.register.models.Lecture;
import org.apache.tomcat.jni.Local;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LectureJdbcTemplateRepository implements LectureRepository {
    private final JdbcTemplate jdbcTemplate;

    public LectureJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Lecture> findAll() {
        final String sql = "SELECT * FROM lecture";
        return jdbcTemplate.query(sql, new LectureMapper());
    }

    @Override
    public Lecture findById(Long lectureId) {
        final String sql = "SELECT * FROM lecture WHERE lecture_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{lectureId}, new LectureMapper());
    }

    @Override
    public Lecture findBySectionId(Long sectionId) {
        final String sql = "SELECT lecture_id, day, start_time, end_time, duration, section_id "
                + "FROM lecture WHERE section_id = ?;";

        return jdbcTemplate.queryForObject(sql, new LectureMapper(), sectionId);
    }


    @Override
    public int add(Lecture lecture) {
        final String sql = "INSERT INTO lecture (day, start_time, end_time, duration, section_id) "
                + "VALUES (?, ?, ?, ?, ?);";

        return jdbcTemplate.update(sql,
                lecture.getDay(),
                java.sql.Time.valueOf(lecture.getStartTime()),  // Convert LocalTime to java.sql.Time
                java.sql.Time.valueOf(lecture.getEndTime()),
                lecture.getDuration(),
                lecture.getSectionId());
    }

    @Override
    public int update(Lecture lecture) {
        final String sql = "UPDATE lecture SET day = ?, start_time = ?, end_time = ?, duration = ?, section_id = ? "
                + "WHERE lecture_id = ?;";

        return jdbcTemplate.update(sql,
                lecture.getDay(),
                java.sql.Time.valueOf(lecture.getStartTime()),  // Convert LocalTime to java.sql.Time
                java.sql.Time.valueOf(lecture.getEndTime()),
                lecture.getDuration(),
                lecture.getSectionId(),
                lecture.getLectureId());
    }

    @Override
    public int deleteById(Long lectureId) {
        final String sql = "DELETE FROM lecture WHERE lecture_id = ?";
        return jdbcTemplate.update(sql, lectureId);
    }
}