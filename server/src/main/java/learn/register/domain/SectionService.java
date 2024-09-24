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
    public Section findSectionById(Long sectionId) {
        return sectionRepository.findById(sectionId);
    }

    // Add a new section
    public Result<Section> addSection(Section section) {
        Result<Section> result = validate(section);
        if (!result.isSuccess()) {
            return result;
        }

        int saveResult = sectionRepository.save(section);

        if (saveResult > 0) {
            result.setType(ResultType.SUCCESS);
            result.setPayload(section);
        } else {
            result.setType(ResultType.ERROR);
            result.setMessage("Could not save the section.");
        }

        return result;
    }

    // Update an existing section
    public Result<Section> updateSection(Long sectionId, Section section) {
        Result<Section> result = validate(section);
        if (!result.isSuccess()) {
            return result;
        }

        section.setSectionId(sectionId); // Set the correct ID
        int updateResult = sectionRepository.update(section);

        if (updateResult > 0) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Section not found.");
        }

        return result;
    }

    // Delete a section by ID
    public Result<Void> deleteSectionById(Long sectionId) {
        Result<Void> result = new Result<>();
        int deleteResult = sectionRepository.deleteById(sectionId);

        if (deleteResult > 0) {
            result.setType(ResultType.SUCCESS);
        } else {
            result.setType(ResultType.NOT_FOUND);
            result.setMessage("Section not found.");
        }

        return result;
    }

    private Result<Section> validate(Section section) {
        Result<Section> result = new Result<>();

        if (section.getAbbreviation() == null || section.getAbbreviation().trim().isEmpty()) {
            result.setType(ResultType.INVALID);
            result.setMessage("Abbreviation is required.");
            return result;
        }

        if (section.getStudentCap() <= 0) {
            result.setType(ResultType.INVALID);
            result.setMessage("Student capacity must be greater than zero.");
            return result;
        }

        if (section.getCourseId() == null) {
            result.setType(ResultType.INVALID);
            result.setMessage("Course ID is required.");
            return result;
        }

        result.setType(ResultType.SUCCESS);
        return result;
    }
}
