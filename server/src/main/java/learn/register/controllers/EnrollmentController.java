package learn.register.controllers;

import learn.register.domain.Result;
import learn.register.models.Enrollment;
import learn.register.domain.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // GET all enrollments
    @GetMapping
    public List<Enrollment> findAll() {
        return enrollmentService.findAllEnrollments();
    }

    // GET enrollment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> findById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.findEnrollmentById(id);
        if (enrollment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(enrollment, HttpStatus.OK);
    }

    // POST - Add a new enrollment
    @PostMapping
    public ResponseEntity<?> addEnrollment(@RequestBody Enrollment enrollment) {
        Result<Enrollment> result = enrollmentService.addEnrollment(enrollment);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // PUT - Update an existing enrollment
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        Result<Enrollment> result = enrollmentService.updateEnrollment(id, enrollment);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // DELETE - Remove an enrollment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Result<Void> result = enrollmentService.deleteEnrollmentById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.NOT_FOUND);
    }
}
