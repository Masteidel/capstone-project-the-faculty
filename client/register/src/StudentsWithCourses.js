import React, { useState, useEffect } from "react";

function StudentsWithCourses() {
    const [students, setStudents] = useState([]);
    const url = 'http://localhost:8080/api/students';

    // Fetch Students with Courses
    useEffect(() => {
        const fetchStudents = async () => {
            try {
                const response = await fetch(url);
                const studentsData = await response.json();
                const studentsWithCourses = await Promise.all(
                    studentsData.map(async student => {
                        const coursesResponse = await fetch(`http://localhost:8080/api/courses/student/${student.studentId}`);
                        const courses = await coursesResponse.json();
                        return { ...student, courses };
                    })
                );

                setStudents(studentsWithCourses);
            } catch (error) {
                console.error("Error fetching students or courses:", error);
            }
        };

        fetchStudents();
    }, []);

    return (
        <div className="container">
            <h3>These are the Courses our Students are Taking!</h3>
            {students.map(student => (
                <div key={student.studentId} className="card mb-3">
                    <div className="card-header">
                        {student.firstName} {student.lastName}
                    </div>
                    <ul className="list-group list-group-flush">
                        {student.courses.length > 0 ? (
                            student.courses.map(course => (
                                <li key={course.courseId} className="list-group-item">
                                    {course.name} - {course.subject} ({course.credits} credits)
                                </li>
                            ))
                        ) : (
                            <li className="list-group-item">No courses enrolled</li>
                        )}
                    </ul>
                </div>
            ))}
        </div>
    );
}

export default StudentsWithCourses;