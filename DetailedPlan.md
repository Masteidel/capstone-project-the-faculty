# Detailed Plan

# High-level Requirements

The system is designed for two main users: Professors and Students, with the following capabilities:

## For Professors:
- **Create a Course**: Professors can create a new course by specifying the course name, subject, credits, and other relevant details.
- **Create a Section**: Professors can create sections for the courses they are teaching, setting the maximum number of students for each section.
- **Create a Lecture**: Professors can create lectures for the sections of the courses they are teaching, specifying the day, time, and duration of the lecture.
- **Edit a Section**: Professors can modify the student limit or lecture details for any future section.
- **Delete a Section**: Professors can remove their involvement in a course by deleting the section they are assigned to.
- **Un-enroll Students**: Professors can remove students from their sections, effectively un-enrolling them from that specific course.

## For Students:
- **Browse Courses**: Students can browse through available courses, with filters for course type, time, and professor.
- **Sign Up for a Course**: Students can enroll in sections, provided that there is space, the time does not conflict with another course they are enrolled in, and they have not exceeded their credit limit.
- **Cancel Enrollment**: Students can un-enroll themselves from a section if they no longer wish to be part of the course.

---

## Data Fields

- **Student**:
  - `student_id`: A unique identifier for the student (primary key).
  - `first_name`: The student's first name.
  - `last_name`: The student's last name.
  - `email`: The student's email address.
  - `phone`: The student's phone number.
  - `major`: The student's declared major.
  - `year`: The student's current year in school.
  - `credits`: The number of credits the student is currently enrolled in.

- **Professor**:
  - `professor_id`: A unique identifier for the professor (primary key).
  - `first_name`: The professor's first name.
  - `last_name`: The professor's last name.
  - `email`: The professor's email address.
  - `phone`: The professor's phone number.

- **Course**:
  - `course_id`: A unique identifier for the course (primary key).
  - `name`: The name of the course.
  - `subject`: The subject of the course (e.g., Math, Science, History).
  - `credits`: The number of credits the course is worth.

- **Section**:
  - `section_id`: A unique identifier for the section (primary key).
  - `abbreviation`: A shortened identifier for the section.
  - `student_cap`: The maximum number of students allowed in the section.
  - `course_id`: Foreign key linking to the course.
  - `professor_id`: Foreign key linking to the professor.

- **Enrollment**:
  - `enrollment_id`: A unique identifier for the enrollment (primary key).
  - `status`: Tracks the student's enrollment status (e.g., Enrolled, Waitlisted).
  - `student_id`: Foreign key linking to the student.
  - `section_id`: Foreign key linking to the section.

- **Lecture**:
  - `lecture_id`: A unique identifier for the lecture (primary key).
  - `day`: The day of the week the lecture takes place.
  - `start_time`: The time the lecture begins.
  - `end_time`: The time the lecture ends.
  - `duration`: The length of the lecture.
  - `section_id`: Foreign key linking to the section.

- **AppUser**:
  - `app_user_id`: A unique identifier for the app user (primary key).
  - `username`: The app user's username.
  - `password_hash`: The hash of the app user's password.
  - `disabled`: Boolean value indicating whether or not the app user's account is enabled.

  - **AppRole**:
  - `app_role_id`: A unique identifier for the app role (primary key).
  - `name`: The name of the app role.

---

## Validation Rules

- **Student Enrollment**:
  - A student cannot be enrolled in multiple lectures that take place at the same time.
  - A student cannot enroll in a section that is at full capacity.
  - The student's total credits cannot exceed the allowed limit.

- **Professor Lectures**:
  - A professor cannot be assigned to teach multiple lectures at the same time.

---

## Technical Requirements

- **Database Usage**: The system will use a relational database to store information about students, professors, courses, sections, enrollments, and lectures.
- **Validation**: Java will be used to enforce validation rules, ensuring that professors and students cannot have overlapping schedules or exceed enrollment limits.
- **Three-Layer Architecture**: The system will be designed with a clear separation between the presentation layer, business logic, and data access.
- **Testing**: The repository and service layers will be fully tested to cover both positive and negative scenarios, ensuring the system functions correctly under different conditions.


## Class Diagram
src
├───main
│   └───java
│       └───learn
│           └───register
│               │   App.java
│               │
│               ├───controllers
│               │       ErrorResponse.java
│               │       GlobalExceptionHandler.java
│               │       StudentController.java
│               │       ProfessorController.java
│               │       CourseController.java
│               │       EnrollmentController.java
│               │       SectionController.java
│               │       LectureController.java
│               │       AuthController.java
│               │
│               ├───domain
│               │       Result.java
│               │       ResultType.java
│               │       Validations.java
│               │       StudentService.java
│               │       ProfessorService.java
│               │       CourseService.java
│               │       EnrollmentService.java
│               │       SectionService.java
│               │       LectureService.java
│               │
│               ├───models
│               │       Student.java
│               │       Professor.java
│               │       Course.java
│               │       Section.java
│               │       Lecture.java
│               │       Enrollment.java
│               │       Status.java     -- Enrollment status enum
│               │       Subject.java    -- Course subject enum
│               │       AppUser.java
│               │       AppRole.java
│               │
│               ├───security
│               │       AppUserService.java
│               │       SecurityConfig.java
│               │
│               └───data
│                   │   StudentRepository.java
│                   │   StudentJdbcTemplateRepository.java
│                   │   ProfessorRepository.java
│                   │   ProfessorJdbcTemplateRepository.java
│                   │   CourseRepository.java
│                   │   CourseJdbcTemplateRepository.java
│                   │   SectionRepository.java
│                   │   SectionJdbcTemplateRepository.java
│                   │   LectureRepository.java
│                   │   LectureJdbcTemplateRepository.java
│                   │   EnrollmentRepository.java
│                   │   EnrollmentJdbcTemplateRepository.java
│                   │   AppUserRepository.java
│                   │   AppUserJdbcTemplateRepository.java
│                   │   AppRoleRepository.java
│                   │   AppRoleJdbcTemplateRepository.java
│                   │
│                   └───mappers
│                           StudentMapper.java
│                           ProfessorMapper.java
│                           CourseMapper.java
│                           SectionMapper.java
│                           LectureMapper.java
│                           EnrollmentMapper.java
│
│
└───test
    └───java
        └───learn
            └───register
                ├───data
                │       StudentJdbcTemplateRepositoryTest.java
                │       ProfessorJdbcTemplateRepositoryTest.java
                │       CourseJdbcTemplateRepositoryTest.java
                │       EnrollJdbcTemplateRepositoryTest.java
                │       SectionJdbcTemplateRepositoryTest.java
                │       LectureJdbcTemplateRepositoryTest.java
                │       AppUserJdbcTemplateRepositoryTest.java
                │       AppRoleJdbcTemplateRepositoryTest.java
                │       KnownGoodState.java
                │
                ├───domain
                │       ControllerServiceTest.java
                │       ProfessorServiceTest.java
                │       CourseServiceTest.java
                │       EnrollmentServiceTest.java
                │       SectionServiceTest.java
                │       LectureServiceTest.java
                │
                └───controllers
                        GlobalExceptionHandlerTest.java
                        StudentControllerTest.java
                        ProfessorControllerTest.java
                        CourseControllerTest.java
                        SectionControllerTest.java
                        LectureControllerTest.java
                        EnrollmentControllerTest.java
                        AuthControllerTest.java


## src/main/java/learn/register

### App.java
**Public Methods:**
- `public static void main(String[] args)` – Starts the application.


# Controller Structure and Method Breakdown

## ErrorResponse.java
- **Purpose**: To encapsulate error details for exception responses.
- **Variables**:
    - `String message`
    - `String status`
    - `List<String> details`
- **Methods**:
    - **Constructor**: Initializes error details.
    - **Getters/Setters**: For accessing and modifying error properties.
    - **Build Method**: Creates an instance of ErrorResponse using a builder pattern.

## GlobalExceptionHandler.java
- **Purpose**: Centralized exception handling for all controllers.
- **Variables**: None
- **Methods**:
    - `handleValidationException`: Handles validation exceptions, returning `ErrorResponse`.
    - `handleDataNotFoundException`: Handles missing data errors, returning `ErrorResponse`.
    - `handleGeneralException`: Generic error handling for any uncaught exceptions.

---

## StudentController.java
- **Variables**:
    - `StudentService studentService`

- **Methods**:
    - `getAllStudents()`: **GET** - Retrieves a list of all students.
    - `getStudentById(int id)`: **GET** - Fetches a specific student by their ID.
    - `addStudent(Student student)`: **POST** - Adds a new student to the system.
    - `updateStudent(int id, Student student)`: **PUT** - Updates the details of a student.
    - `deleteStudent(int id)`: **DELETE** - Removes a student from the system by their ID.

---

## ProfessorController.java
- **Variables**:
    - `ProfessorService professorService`

- **Methods**:
    - `getAllProfessors()`: **GET** - Retrieves a list of all professors.
    - `getProfessorById(int id)`: **GET** - Fetches a specific professor by their ID.
    - `addProfessor(Professor professor)`: **POST** - Adds a new professor.
    - `updateProfessor(int id, Professor professor)`: **PUT** - Updates the details of a professor.
    - `deleteProfessor(int id)`: **DELETE** - Removes a professor by their ID.

---

## CourseController.java
- **Variables**:
    - `CourseService courseService`

- **Methods**:
    - `getAllCourses()`: **GET** - Retrieves all courses.
    - `getCourseById(int id)`: **GET** - Fetches a specific course by its ID.
    - `addCourse(Course course)`: **POST** - Adds a new course.
    - `updateCourse(int id, Course course)`: **PUT** - Updates course details.
    - `deleteCourse(int id)`: **DELETE** - Removes a course by its ID.

---

## EnrollmentController.java
- **Variables**:
    - `EnrollmentService enrollmentService`

- **Methods**:
    - `getAllEnrollments()`: **GET** - Retrieves all enrollments.
    - `getEnrollmentById(int id)`: **GET** - Fetches a specific enrollment by its ID.
    - `addEnrollment(Enrollment enrollment)`: **POST** - Adds a new enrollment for a student in a course.
    - `updateEnrollment(int id, Enrollment enrollment)`: **PUT** - Updates an existing enrollment.
    - `deleteEnrollment(int id)`: **DELETE** - Removes an enrollment by its ID.
    - `getEnrollmentsByStudent(int studentId)`: **GET** - Fetches enrollments by student.
    - `getEnrollmentsByCourse(int courseId)`: **GET** - Fetches enrollments by course.

---

## SectionController.java
- **Variables**:
    - `SectionService sectionService`

- **Methods**:
    - `getAllSections()`: **GET** - Retrieves all sections.
    - `getSectionById(int id)`: **GET** - Fetches a specific section by its ID.
    - `addSection(Section section)`: **POST** - Adds a new section for a course.
    - `updateSection(int id, Section section)`: **PUT** - Updates a section's details.
    - `deleteSection(int id)`: **DELETE** - Removes a section by its ID.

---

## LectureController.java
- **Variables**:
    - `LectureService lectureService`

- **Methods**:
    - `getAllLectures()`: **GET** - Retrieves all lectures.
    - `getLectureById(int id)`: **GET** - Fetches a specific lecture by its ID.
    - `addLecture(Lecture lecture)`: **POST** - Adds a new lecture.
    - `updateLecture(int id, Lecture lecture)`: **PUT** - Updates lecture details.
    - `deleteLecture(int id)`: **DELETE** - Removes a lecture by its ID.

---

## AuthController.java
- **Variables**:
    - `AuthenticationManager authenticationManager`
    - `JwtConverter converter`

  - **Methods**:
    - `authenticate(Map<String, String> credentials)`: Attempts to authenticate the user and returns a ResponseEntity based on whether or not the attempt was successful.


# Domain Structure and Method Breakdown

## Result.java
- **Purpose**: Encapsulates the result of operations, indicating success or failure.
- **Variables**:
    - `boolean success`: Represents if the operation succeeded or failed.
    - `List<String> messages`: Stores error or success messages.
    - `T payload`: Stores the data associated with the result (generic type).
- **Methods**:
    - `addMessage(String message)`: Adds an error or success message to the result.
    - `setPayload(T payload)`: Sets the payload of the result.
    - `isSuccess()`: Returns true if the result indicates success.

---

## ResultType.java
- **Purpose**: Enum for specifying the type of result (e.g., SUCCESS, FAILURE).
- **Values**:
    - `SUCCESS`
    - `INVALID`
    - `NOT_FOUND`

---

## Validations.java
- **Purpose**: Contains validation methods for ensuring domain model constraints.
- **Methods**:
    - `validateStudent(Student student)`: Validates a student's details such as name and email.
    - `validateProfessor(Professor professor)`: Validates professor details like expertise and department.
    - `validateCourse(Course course)`: Ensures that the course has a valid subject, code, and credits.
    - `validateEnrollment(Enrollment enrollment)`: Validates that the enrollment has a student, course, and section.

---

## StudentService.java
- **Purpose**: Provides business logic for managing students.
- **Variables**:
    - `StudentRepository studentRepository`

- **Methods**:
    - `findAll()`: Fetches all students.
    - `findById(int id)`: Finds a student by their ID.
    - `add(Student student)`: Adds a new student, with validation checks.
    - `update(Student student)`: Updates an existing student, with validation.
    - `deleteById(int id)`: Deletes a student if not enrolled in active courses.

---

## ProfessorService.java
- **Purpose**: Provides business logic for managing professors.
- **Variables**:
    - `ProfessorRepository professorRepository`

- **Methods**:
    - `findAll()`: Fetches all professors.
    - `findById(int id)`: Finds a professor by their ID.
    - `add(Professor professor)`: Adds a new professor, with validation.
    - `update(Professor professor)`: Updates an existing professor, with validation.
    - `deleteById(int id)`: Deletes a professor if not assigned to active courses.

---

## CourseService.java
- **Purpose**: Manages course-related business logic.
- **Variables**:
    - `CourseRepository courseRepository`

- **Methods**:
    - `findAll()`: Fetches all courses.
    - `findById(int id)`: Finds a course by its ID.
    - `add(Course course)`: Adds a new course, ensuring proper subject and code.
    - `update(Course course)`: Updates an existing course.
    - `deleteById(int id)`: Deletes a course if no active sections are linked to it.

---

## EnrollmentService.java
- **Purpose**: Handles enrollment-specific business logic.
- **Variables**:
    - `EnrollmentRepository enrollmentRepository`

- **Methods**:
    - `findAll()`: Fetches all enrollments.
    - `findById(int id)`: Finds a specific enrollment by its ID.
    - `add(Enrollment enrollment)`: Adds a new enrollment after validation.
    - `update(Enrollment enrollment)`: Updates an enrollment record.
    - `deleteById(int id)`: Deletes an enrollment if the status is not `COMPLETED`.
    - `findByStudent(int studentId)`: Fetches enrollments by student ID.
    - `findByCourse(int courseId)`: Fetches enrollments by course ID.

---

## SectionService.java
- **Purpose**: Manages section-specific logic.
- **Variables**:
    - `SectionRepository sectionRepository`

- **Methods**:
    - `findAll()`: Fetches all sections.
    - `findById(int id)`: Finds a section by its ID.
    - `add(Section section)`: Adds a new section.
    - `update(Section section)`: Updates an existing section.
    - `deleteById(int id)`: Deletes a section if no active enrollments are linked to it.

---

## LectureService.java
- **Purpose**: Manages lecture-related business logic.
- **Variables**:
    - `LectureRepository lectureRepository`

- **Methods**:
    - `findAll()`: Fetches all lectures.
    - `findById(int id)`: Finds a lecture by its ID.
    - `add(Lecture lecture)`: Adds a new lecture to a section.
    - `update(Lecture lecture)`: Updates a lecture.
    - `deleteById(int id)`: Deletes a lecture if no active attendance records are linked to it.


# Models Structure and Overview

## Student.java
- **Purpose**: Represents a student entity.
- **Variables**:
    - `int id`: Unique identifier for the student.
    - `String firstName`: First name of the student.
    - `String lastName`: Last name of the student.
    - `String email`: Email address of the student.
    - `String major`: Major field of study for the student.
- **Methods**:
    - **Getters/Setters**: Access and modify student details.
    - `toString()`: Provides a string representation of the student.

---

## Professor.java
- **Purpose**: Represents a professor entity.
- **Variables**:
    - `int id`: Unique identifier for the professor.
    - `String firstName`: First name of the professor.
    - `String lastName`: Last name of the professor.
    - `String department`: Department to which the professor belongs.
    - `List<String> expertise`: Areas of expertise for the professor.
- **Methods**:
    - **Getters/Setters**: Access and modify professor details.
    - `toString()`: Provides a string representation of the professor.

---

## Course.java
- **Purpose**: Represents a course entity.
- **Variables**:
    - `int id`: Unique identifier for the course.
    - `String name`: Name of the course.
    - `Subject subject`: Enum representing the course subject (see Subject.java).
    - `int credits`: Number of credits the course is worth.
- **Methods**:
    - **Getters/Setters**: Access and modify course details.
    - `toString()`: Provides a string representation of the course.

---

## Section.java
- **Purpose**: Represents a section of a course.
- **Variables**:
    - `int id`: Unique identifier for the section.
    - `Course course`: The course to which the section belongs.
    - `Professor professor`: The professor teaching the section.
    - `String term`: The academic term during which the section is offered.
- **Methods**:
    - **Getters/Setters**: Access and modify section details.
    - `toString()`: Provides a string representation of the section.

---

## Lecture.java
- **Purpose**: Represents a lecture within a section.
- **Variables**:
    - `int id`: Unique identifier for the lecture.
    - `Section section`: The section to which the lecture belongs.
    - `String topic`: The topic covered in the lecture.
    - `Date date`: The date the lecture was held.
- **Methods**:
    - **Getters/Setters**: Access and modify lecture details.
    - `toString()`: Provides a string representation of the lecture.

---

## Enrollment.java
- **Purpose**: Represents an enrollment of a student in a course.
- **Variables**:
    - `int id`: Unique identifier for the enrollment.
    - `Student student`: The student enrolled in the course.
    - `Section section`: The section the student is enrolled in.
    - `Status status`: The current status of the enrollment (see Status.java).
- **Methods**:
    - **Getters/Setters**: Access and modify enrollment details.
    - `toString()`: Provides a string representation of the enrollment.

---

## Status.java
- **Purpose**: Enum to represent the status of an enrollment.
- **Values**:
    - `ACTIVE`: The student is actively enrolled in the section.
    - `COMPLETED`: The student has completed the section.
    - `DROPPED`: The student dropped the section.

---

## Subject.java
- **Purpose**: Enum to represent the subject of a course.
- **Values**:
    - `MATH`
    - `SCIENCE`
    - `ART`
    - `HISTORY`
    - `COMPUTER_SCIENCE`
    - `ENGLISH`

---

## AppUser.java
- **Purpose**: Represents an app user
- **Variables**:
  - `int app_user_id`: Unique identifier for the app user.
  - `String username`: The app user's username.
  - `String password_hash`: The hash of the app user's password.
  - `boolean disabled`: Boolean value indicating whether or not the app user's account is enabled.
- **Methods**:
  - **Getters/Setters**

---

## AppRole.java
- **Purpose**: Represents an app role for security
- **Variables**:
  - `int app_role_id`: Unique identifier for the app role.
  - `String name`: The app role's name.
- **Methods**:
  - **Getters/Setters**

---

- **AppUser**:
  - `int app_role_id`: Unique identifier for the app role.
  - `String name`: The name of the app role.

# Data Structure and Overview

## StudentRepository.java
- **Purpose**: Interface for managing student data operations.
- **Methods**:
  - `findAll()`: Retrieves all students.
  - `findById(int id)`: Finds a student by their ID.
  - `add(Student student)`: Adds a new student.
  - `update(Student student)`: Updates an existing student.
  - `deleteById(int id)`: Deletes a student by their ID.

---

## StudentJdbcTemplateRepository.java
- **Purpose**: Implementation of `StudentRepository` using `JdbcTemplate` for database interaction.
- **Methods**:
  - Implements all methods from `StudentRepository`.
  - Uses SQL queries for data access and manipulation.

---

## ProfessorRepository.java
- **Purpose**: Interface for managing professor data operations.
- **Methods**:
  - `findAll()`: Retrieves all professors.
  - `findById(int id)`: Finds a professor by their ID.
  - `add(Professor professor)`: Adds a new professor.
  - `update(Professor professor)`: Updates an existing professor.
  - `deleteById(int id)`: Deletes a professor by their ID.

---

## ProfessorJdbcTemplateRepository.java
- **Purpose**: Implementation of `ProfessorRepository` using `JdbcTemplate` for database interaction.
- **Methods**:
  - Implements all methods from `ProfessorRepository`.
  - Uses SQL queries for data access and manipulation.

---

## CourseRepository.java
- **Purpose**: Interface for managing course data operations.
- **Methods**:
  - `findAll()`: Retrieves all courses.
  - `findById(int id)`: Finds a course by its ID.
  - `add(Course course)`: Adds a new course.
  - `update(Course course)`: Updates an existing course.
  - `deleteById(int id)`: Deletes a course by its ID.

---

## CourseJdbcTemplateRepository.java
- **Purpose**: Implementation of `CourseRepository` using `JdbcTemplate` for database interaction.
- **Methods**:
  - Implements all methods from `CourseRepository`.
  - Uses SQL queries for data access and manipulation.

---

## SectionRepository.java
- **Purpose**: Interface for managing section data operations.
- **Methods**:
  - `findAll()`: Retrieves all sections.
  - `findById(int id)`: Finds a section by its ID.
  - `add(Section section)`: Adds a new section.
  - `update(Section section)`: Updates an existing section.
  - `deleteById(int id)`: Deletes a section by its ID.

---

## SectionJdbcTemplateRepository.java
- **Purpose**: Implementation of `SectionRepository` using `JdbcTemplate` for database interaction.
- **Methods**:
  - Implements all methods from `SectionRepository`.
  - Uses SQL queries for data access and manipulation.

---

## LectureRepository.java
- **Purpose**: Interface for managing lecture data operations.
- **Methods**:
  - `findAll()`: Retrieves all lectures.
  - `findById(int id)`: Finds a lecture by its ID.
  - `add(Lecture lecture)`: Adds a new lecture.
  - `update(Lecture lecture)`: Updates an existing lecture.
  - `deleteById(int id)`: Deletes a lecture by its ID.

---

## LectureJdbcTemplateRepository.java
- **Purpose**: Implementation of `LectureRepository` using `JdbcTemplate` for database interaction.
- **Methods**:
  - Implements all methods from `LectureRepository`.
  - Uses SQL queries for data access and manipulation.

---

## EnrollmentRepository.java
- **Purpose**: Interface for managing enrollment data operations.
- **Methods**:
  - `findAll()`: Retrieves all enrollments.
  - `findById(int id)`: Finds an enrollment by its ID.
  - `add(Enrollment enrollment)`: Adds a new enrollment.
  - `update(Enrollment enrollment)`: Updates an existing enrollment.
  - `deleteById(int id)`: Deletes an enrollment by its ID.

---

## EnrollmentJdbcTemplateRepository.java
- **Purpose**: Implementation of `EnrollmentRepository` using `JdbcTemplate` for database interaction.
- **Methods**:
  - Implements all methods from `EnrollmentRepository`.
  - Uses SQL queries for data access and manipulation.

---

## AppUserRepository.java
- **Purpose**: Interface for managing appUser data operations.
- **Methods**:
  - `findByUsername(String username)`: Finds a user by username.
  - `add(AppUser user)`: Adds a user.
  - `update(AppUser user)`: Updates a user.
  - `updateRoles(AppUser user)`: Updates a user's roles.
  - `AppUser map(ResultSet rs, int rowId)`: Maps a SQL row to an AppUser object.
  - `getRolesByUserId(int appUserId)`: Finds the roles of user given their id.

---

## AppUserJdbcTemplateRepository.java
- **Purpose**: Implementation of `AppUserRepository` using `JdbcTemplate` for database interaction.
- **Methods**:
- Implements all methods from `AppUserRepository`.
- Uses SQL queries for data access and manipulation.

---

## AppRoleRepository.java
- **Purpose**: Interface for managing appUser data operations.
- **Methods**:
  - `findById(int id)`: Finds a role by id.
  - `add(AppRole role)`: Adds a role.
  - `update(AppRole role)`: Updates a role.
  - `AppRole map(ResultSet rs, int rowId)`: Maps a SQL row to an AppRole object.

---

## AppRoleJdbcTemplateRepository.java
- **Purpose**: Implementation of `AppRoleRepository` using `JdbcTemplate` for database interaction.
- **Methods**:
- Implements all methods from `AppRoleRepository`.
- Uses SQL queries for data access and manipulation.

# Mappers Structure and Overview

## StudentMapper.java
- **Purpose**: Maps the `Student` model to database rows and vice versa.
- **Methods**:
    - `mapRow(ResultSet rs, int rowNum)`: Maps the result set from the database to a `Student` object.
    - **Returns**: A `Student` object populated with data from the database.

---

## ProfessorMapper.java
- **Purpose**: Maps the `Professor` model to database rows and vice versa.
- **Methods**:
    - `mapRow(ResultSet rs, int rowNum)`: Maps the result set from the database to a `Professor` object.
    - **Returns**: A `Professor` object populated with data from the database.

---

## CourseMapper.java
- **Purpose**: Maps the `Course` model to database rows and vice versa.
- **Methods**:
    - `mapRow(ResultSet rs, int rowNum)`: Maps the result set from the database to a `Course` object.
    - **Returns**: A `Course` object populated with data from the database.

---

## SectionMapper.java
- **Purpose**: Maps the `Section` model to database rows and vice versa.
- **Methods**:
    - `mapRow(ResultSet rs, int rowNum)`: Maps the result set from the database to a `Section` object.
    - **Returns**: A `Section` object populated with data from the database.

---

## LectureMapper.java
- **Purpose**: Maps the `Lecture` model to database rows and vice versa.
- **Methods**:
    - `mapRow(ResultSet rs, int rowNum)`: Maps the result set from the database to a `Lecture` object.
    - **Returns**: A `Lecture` object populated with data from the database.

---

## EnrollmentMapper.java
- **Purpose**: Maps the `Enrollment` model to database rows and vice versa.
- **Methods**:
    - `mapRow(ResultSet rs, int rowNum)`: Maps the result set from the database to an `Enrollment` object.
    - **Returns**: An `Enrollment` object populated with data from the database.

# Security Structure and Overview

## AppUserService.java
- **Purpose**: Provides user management and authentication services.
- **Methods**:
  - **Constructor**
  - `loadUserByUsername(String username)`: Loads user details based on the given username.
  - `add(AppUser user)`: Adds a new user after validating and encoding their password.
  - `validate(AppUser user)`: Validates a user.
  - `ensureAdmin()`: Ensures that an admin user exists in the system. If not, creates one with a random password.

---

## SecurityConfig.java
- **Purpose**: Configures Spring Security for the application.
- **Methods**:
  - `configure(HttpSecurity http)`: Configures HTTP security for the application.
  - `PasswordEncoder getEncoder()`: Returns a `BCryptPasswordEncoder` instance for secure password hashing. 
