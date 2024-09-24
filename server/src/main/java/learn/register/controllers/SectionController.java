package learn.register.controllers;

import learn.register.models.Section;
import learn.register.domain.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    // GET all sections
    @GetMapping
    public List<Section> findAll() {
        return sectionService.findAllSections();
    }

    // GET section by ID
    @GetMapping("/{id}")
    public ResponseEntity<Section> findById(@PathVariable String id) {
        Section section = sectionService.findSectionById(id);
        if (section == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(section, HttpStatus.OK);
    }

    // POST - Add a new section
    @PostMapping
    public ResponseEntity<Section> addSection(@RequestBody Section section) {
        int result = sectionService.addSection(section);
        if (result > 0) {
            return new ResponseEntity<>(section, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // PUT - Update an existing section
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSection(@PathVariable String id, @RequestBody Section section) {
        int result = sectionService.updateSection(id, section);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // DELETE - Remove a section by ID
    // DELETE - Remove a section by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        String result = sectionService.deleteSectionById(id);
        if ("Delete successful".equals(result)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content for success
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found for failure
    }
}
