import React, { useState, useEffect } from "react";

function ProfessorsWithLectures() {
    const [professors, setProfessors] = useState([]);
    const url = 'http://localhost:8080/api/professors';

    // Fetch Professors with Lectures
    useEffect(() => {
        const fetchProfessors = async () => {
            try {
                const response = await fetch(url);
                const professorsData = await response.json();
                const professorsWithLectures = await Promise.all(
                    professorsData.map(async professor => {
                        const lecturesResponse = await fetch(`http://localhost:8080/api/lectures/professor/${professor.professorId}`); // Adjust the URL
                        const lectures = await lecturesResponse.json();
                        return { ...professor, lectures };
                    })
                );

                setProfessors(professorsWithLectures);
            } catch (error) {
                console.error("Error fetching professors or lectures:", error);
            }
        };

        fetchProfessors();
    }, []);

    return (
        <div className="container">
            <h3>This is What our Professors Are Teaching!</h3>
            {professors.map(professor => (
                <div key={professor.professorId} className="card mb-3">
                    <div className="card-header">
                        {professor.firstName} {professor.lastName}
                    </div>
                    <ul className="list-group list-group-flush">
                        {professor.lectures.length > 0 ? (
                            professor.lectures.map(lecture => (
                                <li key={lecture.lectureId} className="list-group-item">
                                    Lecture: {lecture.day} from {lecture.startTime} to {lecture.endTime}
                                </li>
                            ))
                        ) : (
                            <li className="list-group-item">TBA</li>
                        )}
                    </ul>
                </div>
            ))}
        </div>
    );
}

export default ProfessorsWithLectures;