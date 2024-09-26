import React, { useEffect, useState } from "react";
import "./SectionList.css";
import { jwtDecode } from "jwt-decode"; // Import for decoding JWT token

function SectionList() {
    const [sections, setSections] = useState([]);
    const [enrollments, setEnrollments] = useState([]); // Store enrollments
    const [errorMessage, setErrorMessage] = useState("");
    const [newSection, setNewSection] = useState({
        abbreviation: "",
        studentCap: 0,
        courseId: 0,
        professorId: 0,
    });
    const [userRole, setUserRole] = useState(null);

    // Function to extract the user role from the JWT token
    const getUserRole = () => {
        const token = localStorage.getItem("jwt_token");
        if (token) {
            const decodedToken = jwtDecode(token);
            return decodedToken.authorities; // Assuming 'authorities' contains the roles
        }
        return null;
    };

    // Fetch the user role when the component is mounted
    useEffect(() => {
        const role = getUserRole();
        setUserRole(role);
    }, []);

    // Fetch sections when the component is mounted
    useEffect(() => {
        const fetchSections = async () => {
            const token = localStorage.getItem("jwt_token");

            if (!token) {
                setErrorMessage("No token found. Please log in.");
                return;
            }

            try {
                const response = await fetch("http://localhost:8080/api/sections", {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });

                const data = await response.json();
                if (!response.ok || !data) {
                    setErrorMessage("No sections available.");
                } else {
                    setSections(data);
                }

            } catch (error) {
                setErrorMessage(error.message);
                console.error("Error fetching sections:", error);
            }
        };

        fetchSections();
    }, []);

    // Fetch enrollments for professors or students
    useEffect(() => {
        if (userRole && (userRole.includes("ROLE_PROFESSOR") || userRole.includes("ROLE_STUDENT"))) {
            const fetchEnrollments = async () => {
                const token = localStorage.getItem("jwt_token");

                try {
                    const response = await fetch("http://localhost:8080/api/enrollments", {
                        method: "GET",
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            "Content-Type": "application/json",
                        },
                    });

                    const data = await response.json();
                    if (!response.ok || !data) {
                        setErrorMessage("No enrollments available.");
                    } else {
                        setEnrollments(data);
                    }
                } catch (error) {
                    setErrorMessage(error.message);
                    console.error("Error fetching enrollments:", error);
                }
            };

            fetchEnrollments();
        }
    }, [userRole]);

    // Allow students to enroll in a section
    const enrollInSection = async (sectionId) => {
        const token = localStorage.getItem("jwt_token");
        const studentId = localStorage.getItem("studentId"); // Retrieve student ID

        if (!studentId) {
            alert("Student ID not found. Please complete your profile first.");
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/api/enrollments", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ studentId, sectionId, status: "Enrolled" }),
            });

            if (!response.ok) {
                throw new Error("Failed to enroll in section");
            }

            alert("Successfully enrolled in the section!");
        } catch (error) {
            console.error("Error enrolling in section:", error);
        }
    };

    const getNextSectionId = () => {
        if (sections.length === 0) return 1; // If no sections exist, start from 1

        const maxSectionId = sections.reduce((maxId, section) =>
            section.sectionId > maxId ? section.sectionId : maxId, 0);
        return maxSectionId + 1;
    };

    // Add a new section
    const handleAddSection = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem("jwt_token");

        // Assign a new sectionId based on the highest existing sectionId
        const newId = getNextSectionId();
        const sectionToAdd = { ...newSection, sectionId: newId }; // Add the auto-incremented sectionId

        try {
            const response = await fetch("http://localhost:8080/api/sections", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(sectionToAdd),
            });

            const data = await response.json();
            if (!response.ok) {
                throw new Error("Error adding section.");
            }

            setSections([...sections, data]); // Add the new section to the list
            setNewSection({ abbreviation: "", studentCap: 0, courseId: 0, professorId: 0 });

        } catch (error) {
            setErrorMessage(error.message);
            console.error("Error adding section:", error);
        }
    };

    // Delete a section
    const handleDeleteSection = async (sectionId) => {
        const token = localStorage.getItem("jwt_token");

        try {
            const response = await fetch(`http://localhost:8080/api/sections/${sectionId}`, {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
            });

            if (response.ok) {
                setSections(sections.filter((section) => section.sectionId !== sectionId));
            } else {
                throw new Error("Error deleting section.");
            }

        } catch (error) {
            setErrorMessage(error.message);
            console.error("Error deleting section:", error);
        }
    };

    return (
        <div className="section-list">
            
                <h1>Sections List</h1>
                
            
            {errorMessage && <p className="error-message">{errorMessage}</p>}

            {/* Conditionally render Add Section form for professors */}
            {userRole && userRole.includes("ROLE_PROFESSOR") && (
                <form onSubmit={handleAddSection} className="add-section-form">
                    <input
                        type="text"
                        placeholder="Abbreviation"
                        value={newSection.abbreviation}
                        onChange={(e) => setNewSection({ ...newSection, abbreviation: e.target.value })}
                        required
                    />
                    <input
                        type="number"
                        placeholder="Student Cap"
                        value={newSection.studentCap}
                        onChange={(e) => setNewSection({ ...newSection, studentCap: parseInt(e.target.value) || 0 })}
                        required
                    />
                    <input
                        type="number"
                        placeholder="Course ID"
                        value={newSection.courseId}
                        onChange={(e) => setNewSection({ ...newSection, courseId: parseInt(e.target.value) || 0 })}
                        required
                    />
                    <input
                        type="number"
                        placeholder="Professor ID"
                        value={newSection.professorId}
                        onChange={(e) => setNewSection({ ...newSection, professorId: parseInt(e.target.value) || 0 })}
                        required
                    />
                    <button type="submit">Add Section</button>
                </form>
            )}

            {sections.length > 0 ? (
                <table className="section-table">
                    <thead>
                        <tr>
                            <th>Abbreviation</th>
                            <th>Student Cap</th>
                            <th>Course ID</th>
                            <th>Professor ID</th>
                            {userRole && userRole.includes("ROLE_STUDENT") && <th>Actions</th>}
                            {userRole && userRole.includes("ROLE_PROFESSOR") && <th>Enrollments</th>}
                        </tr>
                    </thead>
                    <tbody>
                        {sections.map((section) => (
                            <tr key={section.sectionId}>
                                <td>{section.abbreviation}</td>
                                <td>{section.studentCap}</td>
                                <td>{section.courseId}</td>
                                <td>{section.professorId}</td>

                                {/* Students can enroll in a section */}
                                {userRole && userRole.includes("ROLE_STUDENT") && (
                                    <td>
                                        <button onClick={() => enrollInSection(section.sectionId)}>Enroll</button>
                                    </td>
                                )}

                                {/* Professors can see enrollments for each section */}
                                {userRole && userRole.includes("ROLE_PROFESSOR") && (
                                    <td>
                                        <ul>
                                            {enrollments
                                                .filter((enrollment) => enrollment.sectionId === section.sectionId)
                                                .map((enrollment) => (
                                                    <li key={enrollment.enrollmentId}>
                                                        {`Student: ${enrollment.studentId}, Status: ${enrollment.status}`}
                                                    </li>
                                                ))}
                                        </ul>
                                    </td>
                                )}

                                {/* Professors can delete sections */}
                                {userRole && userRole.includes("ROLE_PROFESSOR") && (
                                    <td>
                                        <button className="delete" onClick={() => handleDeleteSection(section.sectionId)}>Delete</button>
                                    </td>
                                )}
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

export default SectionList;
