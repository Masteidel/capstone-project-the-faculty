import React, { useEffect, useState } from "react";
import './StudentsEnrolled.css'; // Optional for styling
import {jwtDecode} from 'jwt-decode'; // For decoding the token

function StudentsEnrolled() {
  const [studentsEnrolled, setStudentsEnrolled] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  const [userRole, setUserRole] = useState(null);

  // Fetch the professor's courses and the students enrolled
  useEffect(() => {
    const token = localStorage.getItem("jwt_token");
    if (token) {
      const decodedToken = jwtDecode(token);
      setUserRole(decodedToken.authorities); // Assuming "authorities" holds roles

      if (decodedToken.authorities === "ROLE_PROFESSOR") {
        fetchEnrollments(); // Fetch enrollments if the user is a professor
      }
    }
  }, []);

  // Fetch enrollments, students, sections, and courses
  const fetchEnrollments = async () => {
    const token = localStorage.getItem("jwt_token");

    try {
      const enrollmentResponse = await fetch("http://localhost:8080/api/enrollments", {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      const enrollments = await enrollmentResponse.json();
      if (!enrollmentResponse.ok) {
        setErrorMessage("Failed to fetch enrollments.");
        return;
      }

      // Fetch student, section, and course information for each enrollment
      const studentsData = await Promise.all(
        enrollments.map(async (enrollment) => {
          // Fetch the student data
          const studentResponse = await fetch(`http://localhost:8080/api/students/${enrollment.studentId}`, {
            method: "GET",
            headers: {
              "Authorization": `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          });

          const student = await studentResponse.json();

          // Fetch the section data
          const sectionResponse = await fetch(`http://localhost:8080/api/sections/${enrollment.sectionId}`, {
            method: "GET",
            headers: {
              "Authorization": `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          });

          const section = await sectionResponse.json();

          // Fetch the course data using courseId from the section
          const courseResponse = await fetch(`http://localhost:8080/api/courses/${section.courseId}`, {
            method: "GET",
            headers: {
              "Authorization": `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          });

          const course = await courseResponse.json();

          if (studentResponse.ok && sectionResponse.ok && courseResponse.ok) {
            // Combine enrollment with student, section, and course data
            return {
              ...enrollment,
              studentId: enrollment.studentId, // Add studentId
              studentName: student.firstName + ' ' + student.lastName,
              studentEmail: student.email,
              sectionAbbreviation: section.abbreviation, // Assuming section has "abbreviation" field
              courseName: course.name, // Assuming course has "name" field
            };
          } else {
            console.error(`Failed to fetch data for student ID ${enrollment.studentId}, section ID ${enrollment.sectionId}, or course ID ${section.courseId}`);
            return enrollment;
          }
        })
      );

      setStudentsEnrolled(studentsData); // Set the combined data for rendering
    } catch (error) {
      setErrorMessage("Error fetching students, sections, and courses: " + error.message);
      console.error("Error fetching students, sections, and courses:", error);
    }
  };

  // Function to remove a student from a section
  const handleRemoveStudent = async (enrollmentId) => {
    const token = localStorage.getItem("jwt_token");

    try {
      const response = await fetch(`http://localhost:8080/api/enrollments/${enrollmentId}`, {
        method: "DELETE",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        alert("Student removed from the course.");
        setStudentsEnrolled(studentsEnrolled.filter(student => student.enrollmentId !== enrollmentId)); // Update the list
      } else {
        setErrorMessage("Failed to remove student.");
      }
    } catch (error) {
      setErrorMessage("Error removing student: " + error.message);
    }
  };

  return (
    <div className="students-enrolled">
      <h1>Students Enrolled</h1>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      {studentsEnrolled.length > 0 ? (
        <table className="students-table">
          <thead>
            <tr>
              <th>Student ID</th> {/* Added column for Student ID */}
              <th>Student Name</th>
              <th>Email</th>
              <th>Course</th>
              <th>Section</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {studentsEnrolled.map(student => (
              <tr key={student.enrollmentId}>
                <td>{student.studentId}</td> {/* Display student ID */}
                <td>{student.studentName}</td>
                <td>{student.studentEmail}</td>
                <td>{student.courseName}</td>
                <td>{student.sectionAbbreviation}</td>
                <td>
                  <button onClick={() => handleRemoveStudent(student.enrollmentId)}>Remove</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No students enrolled in your courses.</p>
      )}
    </div>
  );
}

export default StudentsEnrolled;
