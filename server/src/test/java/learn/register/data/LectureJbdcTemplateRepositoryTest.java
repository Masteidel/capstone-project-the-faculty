package learn.register.data;

import learn.register.models.Lecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindLecturesBySectionId() {
        List<Lecture> lectures = repository.findBySectionId(1);
        assertNotNull(lectures);
        assertTrue(lectures.size() > 0);
    }

    @Test
    void shouldAddLecture() {
        Lecture lecture = new Lecture();
        lecture.setDay("Monday");
        lecture.setStartTime(LocalTime.of(10, 0));
        lecture.setEndTime(LocalTime.of(12, 0));
        lecture.setDuration(120);
        lecture.setSectionId(1);

        Lecture result = repository.add(lecture);
        assertNotNull(result);
        assertTrue(result.getLectureId() > 0);
    }

    @Test
    void shouldUpdateLecture() {
        Lecture lecture = new Lecture();
        lecture.setLectureId(1);
        lecture.setDay("Tuesday");
        lecture.setStartTime(LocalTime.of(11, 0));
        lecture.setEndTime(LocalTime.of(13, 0));
        lecture.setDuration(120);
        lecture.setSectionId(1);

        assertTrue(repository.update(lecture));

        Lecture updated = repository.findBySectionId(1).get(0); // assuming there's a lecture with sectionId = 1
        assertEquals("Tuesday", updated.getDay());
        assertEquals(LocalTime.of(11, 0), updated.getStartTime());
    }

    @Test
    void shouldDeleteLectureById() {
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1)); // Deleting the same lecture again should return false
    }
}