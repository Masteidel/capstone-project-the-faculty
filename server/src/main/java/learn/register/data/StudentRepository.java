package learn.register.data;

import learn.register.models.Student;

import java.util.List;

public interface StudentRepository {
    List<Student> findAll();
    Student findById(Long studentId);
    int save(Student student);
    int update(Student student);
    int deleteById(Long studentId);
}
