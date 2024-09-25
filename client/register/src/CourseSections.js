import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

function CourseSections() {
    const { courseId } = useParams();
    const [sections, setSections] = useState([]);
    const url = `http://localhost:8080/api/course/${courseId}/sections`;

    // Fetch sections for the course from the API
    useEffect(() => {
        fetch(url)
        .then(response => {
            if(response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected Status Code: ${response.status}`)
            }
        })
        .then(data => setSections(data))
        .catch(console.log)
    }, [courseId]); 

    return (
        <div className="container">
            <h2>Sections for Course {courseId}</h2>
            <ul className="list-group">
                {sections.map(section => (
                    <li key={section.sectionId} className="list-group-item">
                        <div><strong>Section:</strong> {section.abbreviation}</div>
                        <div><strong>Professor:</strong> {section.professor.firstName} {section.professor.lastName}</div>
                        <div><strong>Student Cap:</strong> {section.studentCap}</div>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default CourseSections;