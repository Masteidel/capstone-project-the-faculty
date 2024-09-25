package learn.register.domain;

import learn.register.data.LectureJdbcTemplateRepository;
import learn.register.models.Enrollment;
import learn.register.models.Lecture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LectureServiceTest {

    @Autowired
    LectureService service;

    @MockBean
    LectureJdbcTemplateRepository lectureRepository;

    @Test
    void shouldAddLecture() {
        Lecture lecture = new Lecture();
        lecture.setDay("Monday");
        lecture.setStartTime(LocalTime.of(9, 0));
        lecture.setEndTime(LocalTime.of(10, 30));
        lecture.setDuration(90);
        lecture.setSectionId(1L);

        Lecture mockOut = new Lecture();
        mockOut.setLectureId(1L);
        mockOut.setDay("Monday");
        mockOut.setStartTime(LocalTime.of(9, 0));
        mockOut.setEndTime(LocalTime.of(10, 30));
        mockOut.setDuration(90);
        mockOut.setSectionId(1L);

        when(lectureRepository.add(lecture)).thenReturn(1);

        Result<Lecture> actual = service.addLecture(lecture);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddInvalidLecture() {
        Lecture lecture = new Lecture();
        lecture.setDay(null); // Invalid lecture with missing day

        Result<Lecture> actual = service.addLecture(lecture);
        assertEquals(ResultType.INVALID, actual.getType());

        lecture.setDay("Monday");
        lecture.setStartTime(null); // Invalid lecture with missing start time
        actual = service.addLecture(lecture);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdateLecture() {
        Lecture lecture = new Lecture();
        lecture.setDay("Tuesday");
        lecture.setStartTime(LocalTime.of(11, 0));
        lecture.setEndTime(LocalTime.of(12, 30));
        lecture.setDuration(90);
        lecture.setSectionId(1L);

        when(lectureRepository.update(lecture)).thenReturn(1);

        Result<Lecture> actual = service.updateLecture(1L, lecture);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissingLecture() {
        Lecture lecture = new Lecture();
        lecture.setDay("Tuesday");
        lecture.setStartTime(LocalTime.of(11, 0));
        lecture.setEndTime(LocalTime.of(12, 30));
        lecture.setDuration(90);
        lecture.setSectionId(1L);

        when(lectureRepository.update(lecture)).thenReturn(0);

        Result<Lecture> actual = service.updateLecture(1L, lecture);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldDeleteLecture() {
        Long lectureId = 1L;

        when(lectureRepository.deleteById(lectureId)).thenReturn(1);

        Result<Void> actual = service.deleteLectureById(lectureId);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotDeleteMissingLecture() {
        Long lectureId = 1L;

        when(lectureRepository.deleteById(lectureId)).thenReturn(0);

        Result<Void> actual = service.deleteLectureById(lectureId);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }
}