package learn.register.data;

import learn.register.models.Lecture;

import java.util.List;

public interface LectureRepository {
    List<Lecture> findBySectionId(int sectionId);

    Lecture add(Lecture lecture);

    boolean update(Lecture lecture);

    boolean deleteById(int lectureId);
}