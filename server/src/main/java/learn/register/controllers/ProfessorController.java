package learn.register.controllers;

import learn.register.domain.Result;
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
    public ResponseEntity<?> addProfessor(@RequestBody Professor professor) {
        Result<Professor> result = professorService.addProfessor(professor);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // PUT - Update an existing professor
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfessor(@PathVariable int id, @RequestBody Professor professor) {
        Result<Professor> result = professorService.updateProfessor(id, professor);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // DELETE - Remove a professor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        Result<Void> result = professorService.deleteProfessorById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.NOT_FOUND);
    }

}
