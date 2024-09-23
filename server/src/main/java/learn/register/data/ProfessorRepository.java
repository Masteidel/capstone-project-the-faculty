package learn.register.data;

import learn.register.models.Professor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfessorRepository {

    // Find all professors
    List<Professor> findAll();

    // Find a professor by their ID
    @Transactional
    Professor findById(int professorId);

    // Add a new professor
    Professor add(Professor professor);

    // Update an existing professor
    boolean update(Professor professor);

    // Delete a professor by their ID
    @Transactional
    boolean deleteById(int professorId);
}
