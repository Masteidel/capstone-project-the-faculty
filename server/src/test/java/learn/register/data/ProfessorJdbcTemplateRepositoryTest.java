package learn.register.data;

import learn.register.models.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfessorJdbcTemplateRepositoryTest {

    @Autowired
    ProfessorJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllProfessors() {
        List<Professor> professors = repository.findAll();
        assertNotNull(professors);
        assertTrue(professors.size() > 0);
    }

    @Test
    void shouldFindProfessorById() {
        Professor professor = repository.findById(1);
        assertNotNull(professor);
        assertEquals("David", professor.getFirstName());
        assertEquals("Davis", professor.getLastName());
    }

    @Test
    void shouldAddProfessor() {
        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Doe");
        professor.setEmail("john.doe@example.com");
        professor.setPhone("123-456-7890");


        Professor actual = repository.add(professor);
        assertNotNull(actual);
        assertEquals("John", actual.getFirstName());
        assertEquals("Doe", actual.getLastName());
    }

    @Test
    void shouldUpdateProfessor() {
        Professor professor = new Professor();
        professor.setProfessorId(2); // Updating Rebecca Brown's data
        professor.setFirstName("Rebecca");
        professor.setLastName("Smith");
        professor.setEmail("rebecca.smith@gmail.com");
        professor.setPhone("942-555-4321");


        assertTrue(repository.update(professor));

        Professor updatedProfessor = repository.findById(2);
        assertEquals("Smith", updatedProfessor.getLastName());
    }

    @Test
    void shouldDeleteProfessor() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }
}