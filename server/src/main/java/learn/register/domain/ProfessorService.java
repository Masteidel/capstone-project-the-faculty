package learn.register.domain;

import learn.register.data.ProfessorJdbcTemplateRepository;
import learn.register.models.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorJdbcTemplateRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorJdbcTemplateRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    // Fetch all professors
    public List<Professor> findAllProfessors() {
        return professorRepository.findAll();
    }

    // Fetch a professor by ID
    public Professor findProfessorById(int professorId) {
        return professorRepository.findById(professorId);
    }

    // Add a new professor
    public Result<Professor> addProfessor(Professor professor) {
        Result<Professor> result = validate(professor);
        if (!result.isSuccess()) {
            return result;
        }

        Professor addedProfessor = professorRepository.add(professor);

        if (addedProfessor != null) {
            result.setType(ResultType.SUCCESS);
            result.setPayload(addedProfessor);
        } else {
            result.setType(ResultType.ERROR);
            result.setMessage("Could not save the professor.");
        }

        return result;
    }

    // Update an existing professor
    public Result<Professor> updateProfessor(int professorId, Professor professor) {
        Result<Professor> result = validate(professor);
        if (!result.isSuccess()) {
            return result;
        }

        professor.setProfessorId(professorId); // Set the correct ID
        boolean updateResult = professorRepository.update(professor);

        if (updateResult) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Professor not found.");
        }

        return result;
    }

    // Delete a professor by ID
    public Result<Void> deleteProfessorById(int professorId) {
        Result<Void> result = new Result<>();
        boolean deleteResult = professorRepository.deleteById(professorId);

        if (deleteResult) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Professor not found.");
        }

        return result;
    }

    private Result<Professor> validate(Professor professor) {
        Result<Professor> result = new Result<>();

        if (professor.getFirstName() == null || professor.getFirstName().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("First name is required.");
            return result;
        }

        if (professor.getLastName() == null || professor.getLastName().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("Last name is required.");
            return result;
        }

        if (professor.getEmail() == null || professor.getEmail().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("Email is required.");
            return result;
        }

        result.setType(ResultType.SUCCESS);
        return result;
    }
}