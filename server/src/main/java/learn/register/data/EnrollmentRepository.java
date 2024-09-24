package learn.register.data;

import learn.register.models.Enrollment;

import java.util.List;

public interface EnrollmentRepository {
    List<Enrollment> findAll();
    Enrollment findById(Long enrollmentId);
    int save(Enrollment enrollment);
    int update(Enrollment enrollment);
    int deleteById(Long enrollmentId);
}
