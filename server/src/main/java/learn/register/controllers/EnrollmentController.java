package learn.register.controllers;

import learn.register.models.Enrollment;
import learn.register.services.EnrollmentService;
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
    public ResponseEntity<Enrollment> addEnrollment(@RequestBody Enrollment enrollment) {
        int result = enrollmentService.addEnrollment(enrollment);
        if (result > 0) {
            return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // PUT - Update an existing enrollment
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        int result = enrollmentService.updateEnrollment(id, enrollment);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // DELETE - Remove an enrollment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        int result = enrollmentService.deleteEnrollmentById(id);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
