package learn.register.data;

import org.junit.jupiter.api.Test;
import learn.register.models.Section;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SectionJdbcTemplateRepositoryTest {

    @Autowired
    SectionJdbcTemplateRepository repository;

    Section section;

    @BeforeEach
    void setUp(){
        section = new Section(null, "MATH101", 50, 1L, 2L);
    }

    @Test
    void findAll() {
        List<Section> sections = repository.findAll();
        assertNotNull(sections);
        assertTrue(sections.size() > 0);
    }

    @Test
    void findById() {
        Section section = repository.findById(1L);
        assertNotNull(section);
        assertEquals(1L, section.getSectionId());
        assertEquals("CS101", section.getAbbreviation());
    }

    @Test
    void save() {
        Section newSection = new Section(null, "BIO102", 30, 1L, 2L); // Assuming valid courseId and professorId
        int rowsAffected = repository.save(newSection);
        assertEquals(1, rowsAffected);

        List<Section> sections = repository.findAll();
        assertTrue(sections.stream().anyMatch(s -> "BIO102".equals(s.getAbbreviation())));
    }

    @Test
    void update() {
        Section section = repository.findById(1L);
        section.setAbbreviation("CS102");
        section.setStudentCap(45);

        int rowsAffected = repository.update(section);
        assertEquals(1, rowsAffected);


        Section updatedSection = repository.findById(1L);
        assertEquals("CS102", updatedSection.getAbbreviation());
        assertEquals(45, updatedSection.getStudentCap());
    }

    @Test
    void deleteById() {
        Section newSection = new Section(null, "PHY101", 40, 1L, 2L);
        repository.save(newSection);

        List<Section> sections = repository.findAll();
        Section sectionToDelete = sections.stream().filter(s -> "PHY101".equals(s.getAbbreviation())).findFirst().orElse(null);
        assertNotNull(sectionToDelete);

        int rowsAffected = repository.deleteById(sectionToDelete.getSectionId());
        assertEquals(1, rowsAffected);

        assertThrows(Exception.class, () -> repository.findById(sectionToDelete.getSectionId()));
    }
}