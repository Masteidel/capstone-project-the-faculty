package learn.register.data;

import learn.register.data.mappers.LectureMapper;
import learn.register.models.Lecture;
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
    public List<Lecture> findBySectionId(int sectionId) {
        final String sql = "select lecture_id, day, start_time, end_time, duration, section_id "
                + "from lecture "
                + "where section_id = ?;";

        return jdbcTemplate.query(sql, new LectureMapper(), sectionId);
    }

    @Override
    public Lecture add(Lecture lecture) {
        final String sql = "insert into lecture (day, start_time, end_time, duration, section_id) "
                + "values (?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, lecture.getDay());
            ps.setTime(2, java.sql.Time.valueOf(lecture.getStartTime())); // Convert LocalTime to java.sql.Time
            ps.setTime(3, java.sql.Time.valueOf(lecture.getEndTime()));
            ps.setInt(4, lecture.getDuration());
            ps.setInt(5, lecture.getSectionId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        lecture.setLectureId(keyHolder.getKey().intValue());
        return lecture;
    }

    @Override
    public boolean update(Lecture lecture) {
        final String sql = "update lecture set "
                + "day = ?, "
                + "start_time = ?, "
                + "end_time = ?, "
                + "duration = ?, "
                + "section_id = ? "
                + "where lecture_id = ?;";

        return jdbcTemplate.update(sql,
                lecture.getDay(),
                lecture.getStartTime(),
                lecture.getEndTime(),
                lecture.getDuration(),
                lecture.getSectionId(),
                lecture.getLectureId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int lectureId) {
        return jdbcTemplate.update(
                "delete from lecture where lecture_id = ?;",
                lectureId) > 0;
    }
}