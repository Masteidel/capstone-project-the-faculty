# Detailed Plan

## src/main/java/learn/register

### App.java
**Public Methods:**
- `public static void main(String[] args)` – Starts the application.

### controllers
#### StudentController.java

**Public Methods:**
- `public List<Student> getAllStudents()` – Fetches a list of all students.
- `public ResponseEntity<Student> getStudentById(int studentId)` – Fetches a student by their ID.
- `public ResponseEntity<Student> createStudent(Student student)` – Creates a new student.
- `public ResponseEntity<Void> updateStudent(int studentId, Student student)` – Updates student information.
- `public ResponseEntity<Void> deleteStudent(int studentId)` – Deletes a student.

**Private Methods:**
- Any helper methods for data transformation or validation (if needed).

#### ProfessorController.java

**Public Methods:**
- `public List<Professor> getAllProfessors()` – Fetches all professors.
- `public ResponseEntity<Professor> getProfessorById(int professorId)` – Fetches a professor by ID.
- `public ResponseEntity<Professor> createProfessor(Professor professor)` – Creates a new professor.
- `public ResponseEntity<Void> updateProfessor(int professorId, Professor professor)` – Updates professor information.
- `public ResponseEntity<Void> deleteProfessor(int professorId)` – Deletes a professor.

**Private Methods:**
- Any internal validation methods for professors, if needed.

#### CourseController.java

**Public Methods:**
- `public List<Course> getAllCourses()` – Fetches all courses.
- `public ResponseEntity<Course> getCourseById(int courseId)` – Fetches a course by ID.
- `public ResponseEntity<Course> createCourse(Course course)` – Creates a new course.
- `public ResponseEntity<Void> updateCourse(int courseId, Course course)` – Updates course information.
- `public ResponseEntity<Void> deleteCourse(int courseId)` – Deletes a course.

**Private Methods:**
- Helper methods for course validation or format adjustments.

#### SectionController.java

**Public Methods:**
- `public List<Section> getAllSections()` – Fetches all sections.
- `public ResponseEntity<Section> getSectionById(int sectionId)` – Fetches a section by ID.
- `public ResponseEntity<Section> createSection(Section section)` – Creates a new section.
- `public ResponseEntity<Void> updateSection(int sectionId, Section section)` – Updates a section.
- `public ResponseEntity<Void> deleteSection(int sectionId)` – Deletes a section.

**Private Methods:**
- Methods for section validation or helper functions to adjust section data.

#### EnrollmentController.java

**Public Methods:**
- `public List<Enrollment> getAllEnrollments()` – Fetches all enrollments.
- `public ResponseEntity<Enrollment> getEnrollmentById(int enrollmentId)` – Fetches an enrollment by ID.
- `public ResponseEntity<Enrollment> createEnrollment(Enrollment enrollment)` – Creates a new enrollment.
- `public ResponseEntity<Void> updateEnrollment(int enrollmentId, Enrollment enrollment)` – Updates an enrollment.
- `public ResponseEntity<Void> deleteEnrollment(int enrollmentId)` – Deletes an enrollment.

**Private Methods:**
- Helper methods to handle enrollment-specific validations or transformations.

#### LectureController.java

**Public Methods:**
- `public List<Lecture> getAllLectures()` – Fetches all lectures.
- `public ResponseEntity<Lecture> getLectureById(int lectureId)` – Fetches a lecture by ID.
- `public ResponseEntity<Lecture> createLecture(Lecture lecture)` – Creates a new lecture.
- `public ResponseEntity<Void> updateLecture(int lectureId, Lecture lecture)` – Updates a lecture.
- `public ResponseEntity<Void> deleteLecture(int lectureId)` – Deletes a lecture.

**Private Methods:**
- Validation or format methods to assist with lecture data.

#### GlobalExceptionHandler.java

**Public Methods:**
- `public ResponseEntity<ErrorResponse> handleException(Exception ex)` – Handles generic exceptions.
- `public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex)` – Handles validation-related exceptions.

**Private Methods:**
- `private ErrorResponse createErrorResponse(String message)` – Creates an error response object.

### domain
#### StudentService.java

**Public Methods:**
- `public List<Student> findAllStudents()` – Finds all students.
- `public Student findById(int studentId)` – Finds a student by ID.
- `public boolean add(Student student)` – Adds a new student.
- `public boolean update(Student student)` – Updates student information.
- `public boolean deleteById(int studentId)` – Deletes a student.

**Private Methods:**
- Validation or helper methods, e.g., `private boolean isValidStudent(Student student)`.

#### ProfessorService.java

**Public Methods:**
- `public List<Professor> findAllProfessors()` – Finds all professors.
- `public Professor findById(int professorId)` – Finds a professor by ID.
- `public boolean add(Professor professor)` – Adds a professor.
- `public boolean update(Professor professor)` – Updates professor details.
- `public boolean deleteById(int professorId)` – Deletes a professor.

**Private Methods:**
- Similar validation methods like `private boolean isValidProfessor(Professor professor)`.

#### CourseService.java

**Public Methods:**
- `public List<Course> findAllCourses()` – Finds all courses.
- `public Course findById(int courseId)` – Finds a course by ID.
- `public boolean add(Course course)` – Adds a new course.
- `public boolean update(Course course)` – Updates course information.
- `public boolean deleteById(int courseId)` – Deletes a course.

**Private Methods:**
- `private boolean isValidCourse(Course course)`.

#### EnrollmentService.java

**Public Methods:**
- `public List<Enrollment> findAllEnrollments()` – Finds all enrollments.
- `public Enrollment findById(int enrollmentId)` – Finds an enrollment by ID.
- `public boolean add(Enrollment enrollment)` – Adds a new enrollment.
- `public boolean update(Enrollment enrollment)` – Updates an enrollment.
- `public boolean deleteById(int enrollmentId)` – Deletes an enrollment.

**Private Methods:**
- Enrollment-specific helper functions.

#### SectionService.java

**Public Methods:**
- `public List<Section> findAllSections()` – Finds all sections.
- `public Section findById(int sectionId)` – Finds a section by ID.
- `public boolean add(Section section)` – Adds a section.
- `public boolean update(Section section)` – Updates a section.
- `public boolean deleteById(int sectionId)` – Deletes a section.

**Private Methods:**
- Validation or helper methods, e.g., `private boolean isValidSection(Section section)`.

#### LectureService.java

**Public Methods:**
- `public List<Lecture> findAllLectures()` – Finds all lectures.
- `public Lecture findById(int lectureId)` – Finds a lecture by ID.
- `public boolean add(Lecture lecture)` – Adds a new lecture.
- `public boolean update(Lecture lecture)` – Updates lecture details.
- `public boolean deleteById(int lectureId)` – Deletes a lecture.

**Private Methods:**
- Helper functions for lecture-related data.

### Validations.java

**Public Methods:**
- General static methods like `public static boolean isValidEmail(String email)` – Validates email format.

**Private Methods:**
- If needed, some utility methods for internal usage.

### models
#### Student.java

**Public Methods:**
- Standard getters and setters.
- `public String getFullName()` – Returns the student's full name.

**Private Methods:**
- Internal helper methods for name formatting.

#### Professor.java

**Public Methods:**
- Getters and setters.
- `public String getFullName()` – Returns the professor's full name.

**Private Methods:**
- Formatting helper methods.

#### Course.java

**Public Methods:**
- Getters and setters.
- `public String getCourseInfo()` – Returns course information (course code, name, etc.).

**Private Methods:**
- Internal formatting methods.

#### Section.java, Lecture.java, Enrollment.java

**Public Methods:**
- Getters and setters for each field.
- Section and Lecture might include scheduling or location details.
- Enrollment might include status-related methods like `public boolean isEnrolled()`.

#### Status.java

**Public Methods:**
- `public static Status fromString(String status)` – Maps string input to enum value.

**Private Methods:**
- Enum-specific helper functions, if any.
