package learn.register.controllers;

import learn.register.domain.Result;
import learn.register.models.Section;
import learn.register.domain.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
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
    public ResponseEntity<Section> findById(@PathVariable Long id) {
        Section section = sectionService.findSectionById(id);
        if (section == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(section, HttpStatus.OK);
    }

    // POST - Add a new section
    @PostMapping
    public ResponseEntity<?> addSection(@RequestBody Section section) {
        Result<Section> result = sectionService.addSection(section);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // PUT - Update an existing section
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSection(@PathVariable Long id, @RequestBody Section section) {
        Result<Section> result = sectionService.updateSection(id, section);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // DELETE - Remove a section by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Result<Void> result = sectionService.deleteSectionById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.NOT_FOUND);
    }

}
