package learn.register.domain;

import learn.register.data.LectureJdbcTemplateRepository;
import learn.register.models.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService {

    private final LectureJdbcTemplateRepository lectureRepository;

    @Autowired
    public LectureService(LectureJdbcTemplateRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    // Fetch all lectures
    public List<Lecture> findAll() {
        return lectureRepository.findAll();
    }

    // Fetch a lecture by ID
    public Lecture findLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId);
    }


    // Fetch a single lecture by Section ID
    public Lecture findLectureBySectionId(Long sectionId) {
        return lectureRepository.findBySectionId(sectionId);  // This now returns a single Lecture
    }


    // Add a new lecture
    public Result<Lecture> addLecture(Lecture lecture) {
        Result<Lecture> result = validate(lecture);
        if (!result.isSuccess()) {
            return result;
        }

        int addResult = lectureRepository.add(lecture);

        if (addResult > 0) {
            result.setType(ResultType.SUCCESS);
            result.setPayload(lecture);
        } else {
            result.setType(ResultType.ERROR);
            result.setMessage("Could not save the lecture.");
        }

        return result;
    }

    // Update an existing lecture
    public Result<Lecture> updateLecture(Long lectureId, Lecture lecture) {
        Result<Lecture> result = validate(lecture);
        if (!result.isSuccess()) {
            return result;
        }

        lecture.setLectureId(lectureId); // Set the correct ID
        int updateResult = lectureRepository.update(lecture);

        if (updateResult > 0) {
            result.setType(ResultType.SUCCESS);
            result.setPayload(lecture); // Set the updated lecture as the payload
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Lecture not found.");
        }

        return result;
    }

    // Delete a lecture by ID
    public Result<Void> deleteLectureById(Long lectureId) {
        Result<Void> result = new Result<>();
        int deleteResult = lectureRepository.deleteById(lectureId);

        if (deleteResult > 0) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Lecture not found.");
        }

        return result;
    }

    private Result<Lecture> validate(Lecture lecture) {
        Result<Lecture> result = new Result<>();

        if (lecture.getDay() == null || lecture.getDay().isEmpty()) {
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

        if (lecture.getDuration() <= 0) {
            result.setType(ResultType.INVALID);
            result.setMessage("Duration must be greater than 0.");
            return result;
        }

        if (lecture.getSectionId() == null) {
            result.setType(ResultType.INVALID);
            result.setMessage("Section ID is required.");
            return result;
        }

        result.setType(ResultType.SUCCESS);
        return result;
    }
}