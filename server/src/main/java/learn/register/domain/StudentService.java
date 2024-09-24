package learn.register.domain;

import learn.register.data.StudentRepository;
import learn.register.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Student findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public int saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public int updateStudent(Student student) {
        return studentRepository.update(student);
    }

    public int deleteStudent(Long id) {
        return studentRepository.deleteById(id);
    }
}
