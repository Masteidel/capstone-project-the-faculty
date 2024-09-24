package learn.register.controllers;

import learn.register.domain.Result;
import learn.register.models.Course;
import learn.register.domain.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // GET all courses
    @GetMapping
    public Result<List<Course>> findAll() {
        return courseService.findAllCourses();
    }

    // GET course by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Result<Course> result = courseService.findCourseById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.NOT_FOUND);
    }


    // POST - Add a new course
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        Result<Course> result = courseService.addCourse(course);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // PUT - Update an existing course
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable UUID id, @RequestBody Course course) {
        Result<Course> result = courseService.updateCourse(id, course);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // DELETE - Remove a course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        Result<Void> result = courseService.deleteCourseById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.NOT_FOUND);
    }

}
