package learn.register.data.mappers;

import learn.register.models.Lecture;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LectureMapper implements RowMapper<Lecture> {

    @Override
    public Lecture mapRow(ResultSet resultSet, int i) throws SQLException {
        Lecture lecture = new Lecture();
        lecture.setLectureId(resultSet.getLong("lecture_id"));
        lecture.setDay(resultSet.getString("day"));
        lecture.setStartTime(resultSet.getTime("start_time").toLocalTime());
        lecture.setEndTime(resultSet.getTime("end_time").toLocalTime());
        lecture.setSectionId(resultSet.getLong("section_id"));
        return lecture;
    }
}