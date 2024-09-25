package learn.register.controllers;

import learn.register.domain.Result;
import learn.register.models.Lecture;
import learn.register.domain.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {

    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {this.lectureService = lectureService; }

    // GET all enrollments
    @GetMapping
    public List<Lecture> findAll() {
        return lectureService.findAll();
    }

    // GET enrollment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Lecture> findById(@PathVariable Long id) {
        Lecture lecture = lectureService.findLectureById(id);
        if (lecture == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lecture, HttpStatus.OK);
    }

    // POST - Add a new enrollment
    @PostMapping
    public ResponseEntity<?> addLecture(@RequestBody Lecture lecture) {
        Result<Lecture> result = lectureService.addLecture(lecture);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // PUT - Update an existing enrollment
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLecture(@PathVariable Long id, @RequestBody Lecture lecture) {
        Result<Lecture> result = lectureService.updateLecture(id, lecture);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // DELETE - Remove an enrollment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Result<Void> result = lectureService.deleteLectureById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.NOT_FOUND);
    }

}
