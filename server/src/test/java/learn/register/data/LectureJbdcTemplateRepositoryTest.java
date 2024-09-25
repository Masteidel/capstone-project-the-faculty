package learn.register.data;

import learn.register.models.Lecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LectureJdbcTemplateRepositoryTest {

    @Autowired
    LectureJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        // Cleanup the lecture table before each test
        jdbcTemplate.update("DELETE FROM lecture");
    }


    @Test
    void shouldFindAllLectures() {
        // Insert sample lectures for testing
        jdbcTemplate.update("INSERT INTO lecture (day, start_time, end_time, duration, section_id) VALUES (?, ?, ?, ?, ?)",
                "Monday", LocalTime.of(10, 0), LocalTime.of(12, 0), 120, 1L);
        jdbcTemplate.update("INSERT INTO lecture (day, start_time, end_time, duration, section_id) VALUES (?, ?, ?, ?, ?)",
                "Tuesday", LocalTime.of(14, 0), LocalTime.of(16, 0), 120, 1L);

        List<Lecture> lectures = repository.findAll();
        assertNotNull(lectures);
        assertEquals(2, lectures.size());
    }

    @Test
    void shouldFindLectureBySectionId() {
        // Insert a lecture for testing
        jdbcTemplate.update("INSERT INTO lecture (day, start_time, end_time, duration, section_id) VALUES (?, ?, ?, ?, ?)",
                "Monday", LocalTime.of(10, 0), LocalTime.of(12, 0), 120, 1L);

        Lecture lecture = repository.findBySectionId(1L);
        assertNotNull(lecture);
        assertEquals("Monday", lecture.getDay());
        assertEquals(1L, lecture.getSectionId());
    }

    @Test
    void shouldAddLecture() {
        Lecture lecture = new Lecture();
        lecture.setDay("Thursday");
        lecture.setStartTime(LocalTime.of(10, 0));
        lecture.setEndTime(LocalTime.of(12, 0));
        lecture.setDuration(120);
        lecture.setSectionId(1L); // Use valid section_id

        // Call the add method and get the number of rows affected
        int rowsAffected = repository.add(lecture);
        assertEquals(1, rowsAffected); // Ensure 1 row was inserted

        // Verify insertion
        Long newLectureId = jdbcTemplate.queryForObject("SELECT MAX(lecture_id) FROM lecture", Long.class);
        Lecture insertedLecture = repository.findBySectionId(1L);
        assertNotNull(insertedLecture);
        assertEquals(newLectureId, insertedLecture.getLectureId()); // Ensure it matches the new ID
        assertEquals("Thursday", insertedLecture.getDay());
        assertEquals(LocalTime.of(10, 0), insertedLecture.getStartTime());
        assertEquals(LocalTime.of(12, 0), insertedLecture.getEndTime());
        assertEquals(120, insertedLecture.getDuration());
        assertEquals(1L, insertedLecture.getSectionId());
    }

    @Test
    void shouldUpdateLecture() {
        // Insert a lecture and get the ID
        jdbcTemplate.update("INSERT INTO lecture (day, start_time, end_time, duration, section_id) VALUES (?, ?, ?, ?, ?)",
                "Wednesday", LocalTime.of(14, 0), LocalTime.of(16, 0), 120, 1L);
        Long lectureId = jdbcTemplate.queryForObject("SELECT MAX(lecture_id) FROM lecture", Long.class);

        Lecture lecture = repository.findBySectionId(1L); // Fetch the inserted lecture
        assertNotNull(lecture);

        // Update the lecture properties
        lecture.setDay("Updated Day");
        lecture.setStartTime(LocalTime.of(15, 0));
        lecture.setEndTime(LocalTime.of(17, 0));

        int rowsAffected = repository.update(lecture);
        assertEquals(1, rowsAffected);

        // Verify update
        Lecture updatedLecture = repository.findBySectionId(1L); // Retrieve the updated lecture
        assertEquals("Updated Day", updatedLecture.getDay());
        assertEquals(LocalTime.of(15, 0), updatedLecture.getStartTime());
        assertEquals(LocalTime.of(17, 0), updatedLecture.getEndTime());
    }

    @Test
    void shouldDeleteLecture() {
        // Insert a lecture and get the ID
        jdbcTemplate.update("INSERT INTO lecture (day, start_time, end_time, duration, section_id) VALUES (?, ?, ?, ?, ?)",
                "Thursday", LocalTime.of(10, 0), LocalTime.of(12, 0), 120, 1L);
        Long lectureId = jdbcTemplate.queryForObject("SELECT MAX(lecture_id) FROM lecture", Long.class);

        int rowsAffected = repository.deleteById(lectureId);
        assertEquals(1, rowsAffected);

        // Verify deletion
        assertThrows(Exception.class, () -> repository.findBySectionId(1L)); // Adjust this if needed
    }
}