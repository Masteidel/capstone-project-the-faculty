package learn.register.controllers;

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
    public List<Course> findAll() {
        return courseService.findAllCourses();
    }

    // GET course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable UUID id) {
        Course course = courseService.findCourseById(id);
        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    // POST - Add a new course
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        int result = courseService.addCourse(course);
        if (result > 0) {
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // PUT - Update an existing course
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCourse(@PathVariable UUID id, @RequestBody Course course) {
        int result = courseService.updateCourse(id, course);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // DELETE - Remove a course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        int result = courseService.deleteCourseById(id);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
