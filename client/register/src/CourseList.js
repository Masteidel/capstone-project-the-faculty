import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function CourseList() {
    const [courses, setCourses] = useState([]);
    const url = 'http://localhost:8080/api/courses'

    // Fetch courses from the API
    useEffect(() => {
        fetch(url)
        .then(response => {
            if(response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected Status Code: ${response.status}`)
            }
        })
        .then(data => setCourses(data))
        .catch(console.log)
    }, []); 

    return (
        <div className="container">
            <h2>Courses</h2>
            <ul className="list-group">
                {courses.map(course => (
                    <li key={course.courseId} className="list-group-item d-flex justify-content-between align-items-center">
                        {course.name} - {course.subject} ({course.credits} credits)
                        <Link to={`/course/${course.courseId}/sections`} className="btn btn-primary">
                            View Sections
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default CourseList;