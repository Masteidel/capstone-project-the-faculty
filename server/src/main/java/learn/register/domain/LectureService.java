package learn.register.domain;

import learn.register.data.LectureJdbcTemplateRepository;
import learn.register.models.Lecture;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService {

    private final LectureJdbcTemplateRepository lectureRepository;

    public LectureService(LectureJdbcTemplateRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    // Fetch all lectures by section ID
    public List<Lecture> findAllLectures(int sectionId) {
        return lectureRepository.findBySectionId(sectionId);
    }

    // Fetch a lecture by ID
    public Lecture findLectureById(int lectureId) {
        return lectureRepository.findBySectionId(lectureId).stream()
                .filter(lecture -> lecture.getLectureId() == lectureId)
                .findFirst()
                .orElse(null);
    }

    // Add a new lecture
    public Lecture add(Lecture lecture) {
        return lectureRepository.add(lecture);
    }

    // Update an existing lecture
    public boolean update(int lectureId, Lecture lecture) {
        lecture.setLectureId(lectureId); // Ensure the correct ID is set
        return lectureRepository.update(lecture);
    }

    // Delete a lecture by ID
    public boolean delete(int lectureId) {
        return lectureRepository.deleteById(lectureId);
    }
}