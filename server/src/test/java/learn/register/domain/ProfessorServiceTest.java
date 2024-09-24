package learn.register.domain;

import learn.register.data.ProfessorJdbcTemplateRepository;
import learn.register.models.Professor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProfessorServiceTest {

    @Autowired
    ProfessorService service;

    @MockBean
    ProfessorJdbcTemplateRepository professorRepository;

    @Test
    void shouldAddProfessor() {
        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Doe");
        professor.setEmail("john.doe@example.com");
        professor.setPhone("123-456-7890");

        Professor mockOut = new Professor();
        mockOut.setProfessorId(1);
        mockOut.setFirstName("John");
        mockOut.setLastName("Doe");
        mockOut.setEmail("john.doe@example.com");
        mockOut.setPhone("123-456-7890");

        when(professorRepository.add(professor)).thenReturn(mockOut);

        Result<Professor> actual = service.addProfessor(professor);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddInvalidProfessor() {
        Professor professor = new Professor();
        professor.setFirstName(null); // Invalid professor with missing first name

        Result<Professor> actual = service.addProfessor(professor);
        assertEquals(ResultType.INVALID, actual.getType());

        professor.setFirstName("John");
        professor.setLastName(" "); // Invalid professor with empty last name
        actual = service.addProfessor(professor);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdateProfessor() {
        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Doe");
        professor.setEmail("john.doe@example.com");

        when(professorRepository.update(professor)).thenReturn(true);

        Result<Professor> actual = service.updateProfessor(1, professor);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissingProfessor() {
        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Doe");
        professor.setEmail("john.doe@example.com");

        when(professorRepository.update(professor)).thenReturn(false);

        Result<Professor> actual = service.updateProfessor(1, professor);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldDeleteProfessor() {
        int professorId = 1;

        when(professorRepository.deleteById(professorId)).thenReturn(true);

        Result<Void> actual = service.deleteProfessorById(professorId);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotDeleteMissingProfessor() {
        int professorId = 1;

        when(professorRepository.deleteById(professorId)).thenReturn(false);

        Result<Void> actual = service.deleteProfessorById(professorId);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }
}
