## Capstone Proposal

# 1. Problem Statement
Being able to enroll in courses provided by professors.

# 2. Technical Solution
Using MySQL as a database, Java for the backend, and React for the frontend, while saving data to AWS. Additionally, we will be using the SendGrid API to send registration confirmation emails to students for the upcoming semester.

## Scenario 1
A student wants to sign up for classes for the following semester. The college uses our registration website in order to sign up for classes. They can view the professor teaching the class, the time the class starts and ends, how many credits the class is worth, and the number of students enrolled.

# 3. Glossary

**Student**: A user who can browse and enroll in available courses that are offered by professors
- `student_id (pk)`
- `first_name`
- `last_name`
- `email`
- `phone`
- `major`
- `year`
- `credits`

**Professor**: A user that can create and manage courses and can view enrolled students on their course roster, remove and add students from class
- `professor_id (pk)`
- `first_name`
- `last_name`
- `email`
- `phone`

**Course**: A class that is offered by a professor with specific details such as time, location, and availability
- `course_id (pk)`
- `name`
- `subject`
- `credits`

**Enrollment**: Tracks student enrollment status for each class a student is enrolled in, tracks the specific section of the course the student is taking
- `application_id (pk)`
- `status`
- `student_id (fk)`
- `section_id (fk)`

**Section**: Represents a more detailed specific offering of the course, including the location, the professor, and the maximum number of students
- `section_id (pk)`
- `name`
- `student_cap`
- `building`
- `course_id (fk)`
- `professor_id (fk)`

**Lecture**: Represents the individual lectures that will be held for a specific section of the course.
- `lecture_id (pk)`
- `day`
- `start_time`
- `end_time`
- `duration`
- `section_id (fk)`

# 4. High Level Requirement

Briefly describe what each user role/authority can do. (These are user stories.)

- Create a course (Professor)
- Register for a course (Student, Professor)
- Edit a future course (Student, Professor)
- Cancel a course (Student, Professor)
- Browse Courses (Anyone)
- Sign up for a course (Authenticated user)

# 5. User Stories/Scenarios

## Create a Course
Create a course that students can join.

- Brief description of the course (e.g., Intro to Abstract Math)
- Date and time (in the future)
- Location (based on building numbers: Building A - Math, Building B - Science, Building C - History, Building D - English)
- Maximum students
- Number of credits (1-4)

**Precondition**: User must be logged in as a Professor.  
**Post-condition**: If the user is a Professor, they can create a course for everyone else to see.

## Edit a Course
Can only edit a course in the future.

**Precondition**: User must be logged in as a Professor. The course must be in the future.  
**Post-condition**: If the user is a Professor, they can edit a course from the future for everyone else to see.

## Cancel a Course Enrollment
Can only cancel a course in the future.

**Precondition**: User must be logged in as a Student or Professor. The course date/time must be in the future.  
**Post-condition**: The course is not deleted. The student is removed from enrollment in that course.

## Browse Courses
Display available courses to anyone using the application.

- **Text-based**: Users filter by course type. Display results as HTML with an action UI to sign up.

**Precondition**: None  
**Post-condition**: None

## Sign Up for a Course
Once a student finds a course they're interested in, they can sign up.

**Precondition**: User must be logged in. The course must not be over capacity and cannot exceed the student's credit limit. The student cannot already be registered for that course or for another course at the same time.  
**Post-condition**: The student is registered for the course.