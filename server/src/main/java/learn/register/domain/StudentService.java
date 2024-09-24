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

    public Result<Student> saveStudent(Student student) {
        Result<Student> result = validate(student);
        if (!result.isSuccess()) {
            return result;
        }

        int saveResult = studentRepository.save(student);

        if (saveResult > 0) {
            result.setType(ResultType.SUCCESS);
            result.setPayload(student);
        } else {
            result.setType(ResultType.ERROR);
            result.setMessage("Could not save the student.");
        }

        return result;
    }

    public Result<Student> updateStudent(Student student) {
        Result<Student> result = validate(student);
        if (!result.isSuccess()) {
            return result;
        }

        int updateResult = studentRepository.update(student);

        if (updateResult > 0) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Student not found.");
        }

        return result;
    }

    public Result<Void> deleteStudent(Long id) {
        Result<Void> result = new Result<>();
        int deleteResult = studentRepository.deleteById(id);

        if (deleteResult > 0) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Student not found.");
        }

        return result;
    }

    private Result<Student> validate(Student student) {
        Result<Student> result = new Result<>();

        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("First name is required.");
            return result;
        }

        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("Last name is required.");
            return result;
        }

        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("Email is required.");
            return result;
        }

        if (student.getMajor() == null || student.getMajor().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("Major is required.");
            return result;
        }

        if (student.getYear() <= 0) {
            result.setType(ResultType.INVALID);
            result.setMessage("Year must be greater than zero.");
            return result;
        }

        result.setType(ResultType.SUCCESS);
        return result;
    }
}
