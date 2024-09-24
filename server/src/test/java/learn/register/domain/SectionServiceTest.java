package learn.register.domain;

import learn.register.data.SectionRepository;
import learn.register.models.Section;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SectionServiceTest {

    @Autowired
    SectionService service;

    @MockBean
    SectionRepository sectionRepository;

    @Test
    void shouldAddSection() {
        Section section = new Section();
        section.setAbbreviation("CS101");
        section.setStudentCap(30);
        section.setCourseId(1L);
        section.setProfessorId(2L);

        when(sectionRepository.save(section)).thenReturn(1);

        Result<Section> actual = service.addSection(section);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(section, actual.getPayload());
    }

    @Test
    void shouldNotAddInvalidSection() {
        Section section = new Section();
        section.setAbbreviation(null); // Invalid section with missing abbreviation

        Result<Section> actual = service.addSection(section);
        assertEquals(ResultType.INVALID, actual.getType());

        section.setAbbreviation("CS101");
        section.setStudentCap(-1); // Invalid section with negative student capacity
        actual = service.addSection(section);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdateSection() {
        Section section = new Section();
        section.setAbbreviation("CS101");
        section.setStudentCap(30);
        section.setCourseId(1L);
        section.setProfessorId(2L);

        when(sectionRepository.update(section)).thenReturn(1);

        Result<Section> actual = service.updateSection(1L, section);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissingSection() {
        Section section = new Section();
        section.setAbbreviation("CS101");
        section.setStudentCap(30);
        section.setCourseId(1L);
        section.setProfessorId(2L);

        when(sectionRepository.update(section)).thenReturn(0);

        Result<Section> actual = service.updateSection(1L, section);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldDeleteSection() {
        Long sectionId = 1L;

        when(sectionRepository.deleteById(sectionId)).thenReturn(1);

        Result<Void> actual = service.deleteSectionById(sectionId);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotDeleteMissingSection() {
        Long sectionId = 1L;

        when(sectionRepository.deleteById(sectionId)).thenReturn(0);

        Result<Void> actual = service.deleteSectionById(sectionId);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }
}