## Capstone Proposal

# 1. Problem Statement
Keeping track of what professors are teaching what courses and when, and what students are enrolled in each course and when can be difficult and confusing. Without some method of tracking this and preventative measures, it is very easy to double book or over book classes. For instance, professors could be scheduled to teach multiple classes at the same time, or students may enroll in courses that have already reached their maximum capacity. These may create challenges that could lead to administrative headaches, scheduling conflicts, and confusion for students and faculty. Having a way to properly track and prevent these issues is essential to ensure courses are properly managed, where both students and professors have conflict-free schedules.

# 2. Technical Solution
Use a database to keep track of what professors are teaching what courses and at what times. A professor can teach a course by creating a section of that course with a given maximum number of students and assigning lectures that take place on given days of the week at given times. Using Java for validation, the application will prevent the same professor from teaching multiple lectures at the same time. Multiple professors can teach the same course at the same times. Likewise students cannot enroll in a section of a course that has lectures at the same time as another section that they are enrolled in. Students cannot enroll in a section that has the maximum number of students. Students can be removed from a section by themselves or by the professor of that section.

# Scenario for student
A student wants to sign up for classes for the following semester, the college uses our registration website in order to sign up for classes. They can view the professor teaching the class, the time the class starts and ends, how many credits the class is worth, and the amount of students enrolled.

# Scenario for Professor
A Professor wants to create a new course, they select what type of course this is going to be (math, science, history, ect). The name of the course, and any details that could help explain information about the course for the student. Next the professor changes how many credits the course will be worth, which will contribute to how many courses a student can take. Lastly they set the amount of the students who can register for there course, once this class is posted they can alter the section the course will take place in. Whether that be in the am or pm section.

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
- `abbreviation`
- `student_cap`
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

- Create a course (Professor) - The Professor can create a brand new course 
- Create a section for a course (Professor) - The Professor can create a brand new section for a specific course
- Create a lecture for a section (Professor) - The Professor can create a brand new lecture for a specific section 
- Edit a future section (Student, Professor) - Professor can edit a section by changing student limit and lecture time and day
- Delete a section (Professor) - The Professor can delete his involvement in a section meaning he won't be teaching it anymore
- Un-enroll a student from an existing section (Student, Professor) - The Student and Professor can both un-enroll that student from a section
- Browse Courses (Anyone)
- Sign up for a course (Authenticated user) - In order to sign up for a section student must be logged in

# 5. User Stories/Scenarios

## Create a Course
Create a course that students can join.

- Brief description of the course (e.g., Intro to Abstract Math)
- Days and time
- Maximum students
- Number of credits (1–4)

**Precondition**: User must be logged in as a Professor.  
**Post-condition**: If the user is a Professor, they can create a course for everyone else to see.

## Create a section
Create a section for students to join
- Choose a course to teach
- Set the maximum number of students

**Precondition**: The user must be logged as a Professor.
**Post-condition**: If the user is a Professor they can create a section for a course for everyone to see.


## Edit a Section
- Edit the student cap

**Precondition**: User must be logged in as a Professor.
**Post-condition**: If the user is a Professor, they can edit a course for everyone else to see.

## Cancel a Section Enrollment
- Remove a student from a section

**Precondition**: User must be logged in as a Student or Professor. 
**Post-condition**: The section is not deleted. The student is removed from enrollment in that course.

## Browse Courses
Display available courses to anyone using the application.

- **Text-based**: Users filter by course type. Display results as HTML with an action UI to sign up.

**Precondition**: None  
**Post-condition**: None

## Sign Up for a Course
Once a student finds a course they're interested in, they can sign up.

**Precondition**: User must be logged in. The course must not be over capacity and cannot exceed the student's credit limit. The student cannot already be registered for that course or for another course at the same time.  
**Post-condition**: The student is registered for the course.