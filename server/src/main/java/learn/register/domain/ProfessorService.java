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
    public Professor addProfessor(Professor professor) {
        return professorRepository.add(professor);
    }

    // Update an existing professor
    public boolean updateProfessor(int professorId, Professor professor) {
        professor.setProfessorId(professorId); // Ensure the correct ID is set
        return professorRepository.update(professor);
    }

    // Delete a professor by ID
    public boolean deleteProfessorById(int professorId) {
        return professorRepository.deleteById(professorId);
    }
}
