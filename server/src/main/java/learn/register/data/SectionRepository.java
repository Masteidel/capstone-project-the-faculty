package learn.register.data;

import learn.register.models.Section;

import java.util.List;

public interface SectionRepository {
    List<Section> findAll();
    Section findById(String sectionId);
    int save(Section section);
    int update(Section section);
    String deleteById(String sectionId);
}
