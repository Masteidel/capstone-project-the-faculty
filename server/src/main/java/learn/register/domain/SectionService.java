package learn.register.domain;

import learn.register.models.Section;
import learn.register.data.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    // Fetch all sections
    public List<Section> findAllSections() {
        return sectionRepository.findAll();
    }

    // Fetch a section by ID
    public Section findSectionById(String sectionId) {
        return sectionRepository.findById(sectionId);
    }

    // Add a new section
    public int addSection(Section section) {
        return sectionRepository.save(section);
    }

    // Update an existing section
    public int updateSection(String sectionId, Section section) {
        section.setSectionId(sectionId); // Ensure the correct ID is set
        return sectionRepository.update(section);
    }

    // Delete a section by ID
    public String deleteSectionById(String sectionId) {
        return sectionRepository.deleteById(sectionId);
    }
}
