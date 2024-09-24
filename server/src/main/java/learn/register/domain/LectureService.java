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
    public Result<Lecture> add(Lecture lecture) {
        Result<Lecture> result = validate(lecture);
        if (!result.isSuccess()) {
            return result;
        }

        Lecture addedLecture = lectureRepository.add(lecture);

        if (addedLecture != null) {
            result.setType(ResultType.SUCCESS);
            result.setPayload(addedLecture);
        } else {
            result.setType(ResultType.ERROR);
            result.setMessage("Could not save the lecture.");
        }

        return result;
    }

    // Update an existing lecture
    public Result<Lecture> update(int lectureId, Lecture lecture) {
        Result<Lecture> result = validate(lecture);
        if (!result.isSuccess()) {
            return result;
        }

        lecture.setLectureId(lectureId); // Ensure the correct ID is set
        boolean updateResult = lectureRepository.update(lecture);

        if (updateResult) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Lecture not found.");
        }

        return result;
    }

    // Delete a lecture by ID
    public Result<Void> delete(int lectureId) {
        Result<Void> result = new Result<>();
        boolean deleteResult = lectureRepository.deleteById(lectureId);

        if (deleteResult) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Lecture not found.");
        }

        return result;
    }

    private Result<Lecture> validate(Lecture lecture) {
        Result<Lecture> result = new Result<>();

        if (lecture.getDay() == null || lecture.getDay().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("Day is required.");
            return result;
        }

        if (lecture.getStartTime() == null) {
            result.setType(ResultType.INVALID);
            result.setMessage("Start time is required.");
            return result;
        }

        if (lecture.getEndTime() == null) {
            result.setType(ResultType.INVALID);
            result.setMessage("End time is required.");
            return result;
        }

        result.setType(ResultType.SUCCESS);
        return result;
    }
}