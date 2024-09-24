package learn.register.controllers;

import learn.register.models.Professor;
import learn.register.domain.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    // GET all professors
    @GetMapping
    public List<Professor> findAll() {
        return professorService.findAllProfessors();
    }

    // GET professor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Professor> findById(@PathVariable int id) {
        Professor professor = professorService.findProfessorById(id);
        if (professor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(professor, HttpStatus.OK);
    }

    // POST - Add a new professor
    @PostMapping
    public ResponseEntity<Professor> addProfessor(@RequestBody Professor professor) {
        Professor result = professorService.addProfessor(professor);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // PUT - Update an existing professor
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProfessor(@PathVariable int id, @RequestBody Professor professor) {
        if (professorService.updateProfessor(id, professor)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // DELETE - Remove a professor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        if (professorService.deleteProfessorById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
