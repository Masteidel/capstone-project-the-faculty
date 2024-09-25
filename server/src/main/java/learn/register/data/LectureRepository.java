package learn.register.data;

import learn.register.models.Lecture;

import java.util.List;

public interface LectureRepository {
    List<Lecture> findAll();

    Lecture findById(Long lectureId);

    Lecture findBySectionId(Long sectionId);

    int add(Lecture lecture);

    int update(Lecture lecture);

    int deleteById(Long lectureId);
}