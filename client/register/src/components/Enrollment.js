import React, { useEffect, useState } from "react";
import "./Enrollment.css";

function Enrollment({ studentId }) {
    const [sections, setSections] = useState([]);
    const [enrollments, setEnrollments] = useState([]); // Store current enrollments
    const [errorMessage, setErrorMessage] = useState("");
    const [loading, setLoading] = useState(true); // Loading state to indicate data fetching

    // Fetch sections and enrollments when the component is mounted
    useEffect(() => {
        const fetchData = async () => {
            const token = localStorage.getItem("jwt_token");

            if (!token) {
                setErrorMessage("No token found. Please log in.");
                return;
            }

            try {
                // Fetch sections
                const sectionsResponse = await fetch("http://localhost:8080/api/sections", {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });

                const sectionsData = await sectionsResponse.json();
                if (sectionsResponse.ok) {
                    setSections(sectionsData);
                } else {
                    setErrorMessage("Failed to fetch sections.");
                }

                // Fetch current enrollments for the student
                const enrollmentsResponse = await fetch(`http://localhost:8080/api/enrollments/student/${studentId}`, {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });

                const enrollmentsData = await enrollmentsResponse.json();
                console.log("Fetched enrollments:", enrollmentsData); // Log the enrollments

                if (enrollmentsResponse.ok) {
                    setEnrollments(enrollmentsData);
                } else {
                    
                }

            } catch (error) {
                setErrorMessage("Error fetching sections or enrollments: " + error.message);
                console.error("Error fetching sections or enrollments:", error);
            } finally {
                setLoading(false); // Set loading to false after data is fetched
            }
        };

        fetchData();
    }, [studentId]);

    // Check if the student is already enrolled in a section
    const isEnrolledInSection = (sectionId) => {
        return enrollments.some(enrollment => enrollment.sectionId === sectionId);
    };

    // Handle enrollment when the "Enroll" button is clicked
    const handleEnroll = async (sectionId) => {
        const token = localStorage.getItem("jwt_token");

        if (isEnrolledInSection(sectionId)) {
            alert("You are already enrolled in this section.");
            return;
        }

        const newEnrollment = {
            studentId: studentId,
            sectionId: sectionId,
            status: "ENROLLED", // Set the enrollment status
        };

        console.log("Enrollment data being sent:", newEnrollment);

        try {
            const response = await fetch("http://localhost:8080/api/enrollments", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newEnrollment),
            });

            const responseText = await response.text();
            console.log("Raw server response:", responseText);

            if (!response.ok) {
                throw new Error(`Error enrolling in section: ${responseText}`);
            }

            alert("Successfully enrolled in the section!");

            // Update the enrollments list
            setEnrollments([...enrollments, newEnrollment]);

        } catch (error) {
            setErrorMessage("Error enrolling in section: " + error.message);
            console.error("Error enrolling in section:", error);
        }
    };

    // If data is still loading, show a loading indicator
    if (loading) {
        return <p>Loading sections and enrollments...</p>;
    }

    return (
        <div className="enrollment-table-container">
            <h1>Enrollment</h1>
            {errorMessage && <p className="error-message">{errorMessage}</p>}

            {sections.length > 0 ? (
                <table className="enrollment-table">
                    <thead>
                        <tr>
                            <th>Section ID</th>
                            <th>Abbreviation</th>
                            <th>Student Cap</th>
                            <th>Course ID</th>
                            <th>Professor ID</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {sections.map((section) => (
                            <tr key={section.sectionId}>
                                <td>{section.sectionId}</td>
                                <td>{section.abbreviation}</td>
                                <td>{section.studentCap}</td>
                                <td>{section.courseId}</td>
                                <td>{section.professorId}</td>
                                <td>
                                    <button
                                        onClick={() => handleEnroll(section.sectionId)}
                                        disabled={isEnrolledInSection(section.sectionId)}
                                    >
                                        {isEnrolledInSection(section.sectionId) ? "Enrolled" : "Enroll"}
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>No sections available at the moment.</p>
            )}
        </div>
    );
}

export default Enrollment;
