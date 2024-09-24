package learn.register.data;

import org.junit.jupiter.api.Test;
import learn.register.models.Section;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SectionJdbcTemplateRepositoryTest {

    @Autowired
    SectionJdbcTemplateRepository repository;

    Section section;

    @BeforeEach
    void setUp(){
        section = new Section(null, "MATH101", 50, UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    @Test
    void findAll() {
        List<Section> sections = repository.findAll();
        assertNotNull(sections);
        assertTrue(sections.size() > 0);
    }

    @Test
    void findById() {
        Section section = repository.findById("1"); // Pass sectionId as String
        assertNotNull(section);
        assertEquals("1", section.getSectionId());  // Compare sectionId as String
        assertEquals("CS102", section.getAbbreviation());
    }

    @Test
    void save() {
        Section newSection = new Section(null, "BIO102", 30, UUID.randomUUID().toString(), UUID.randomUUID().toString());  // Use UUIDs for courseId and professorId
        int rowsAffected = repository.save(newSection);
        assertEquals(1, rowsAffected);

        List<Section> sections = repository.findAll();
        assertTrue(sections.stream().anyMatch(s -> "BIO102".equals(s.getAbbreviation())));
    }

    @Test
    void update() {
        Section section = repository.findById("1"); // Pass sectionId as String
        section.setAbbreviation("CS102");
        section.setStudentCap(45);

        int rowsAffected = repository.update(section);
        assertEquals(1, rowsAffected);

        Section updatedSection = repository.findById("1"); // Pass sectionId as String
        assertEquals("CS102", updatedSection.getAbbreviation());
        assertEquals(45, updatedSection.getStudentCap());
    }

    @Test
    void deleteById() {
        // Create a new section with UUIDs for courseId and professorId
        Section newSection = new Section(null, "PHY101", 40, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        repository.save(newSection);

        // Retrieve the list of sections and find the one just created
        List<Section> sections = repository.findAll();
        Section sectionToDelete = sections.stream()
                .filter(s -> "PHY101".equals(s.getAbbreviation()))
                .findFirst().orElse(null);
        assertNotNull(sectionToDelete);

        // Pass the String UUID (sectionId) to the deleteById method and check the return message
        String result = repository.deleteById(sectionToDelete.getSectionId());
        assertEquals("Delete successful", result);

        // Ensure that the section is actually deleted
        assertThrows(Exception.class, () -> repository.findById(sectionToDelete.getSectionId()));
    }
}