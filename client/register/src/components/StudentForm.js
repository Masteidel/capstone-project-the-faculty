import React, { useState, useEffect } from "react";
import Enrollment from "./Enrollment"; // Import the Enrollment component
import "./StudentForm.css";

function StudentForm() {
    const [studentData, setStudentData] = useState({
        studentId: "", // Auto-generate this value based on existing students
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        major: "",
        year: "",
        credits: 0,
    });
    const [students, setStudents] = useState([]); // Store the list of students
    const [isProfileComplete, setIsProfileComplete] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    // Fetch all students and find the next available studentId
    useEffect(() => {
        const fetchStudents = async () => {
            const token = localStorage.getItem("jwt_token");
    
            try {
                const response = await fetch("http://localhost:8080/api/students", {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });
    
                const data = await response.json();
                if (response.ok) {
                    setStudents(data); // Store the list of students
                    const nextStudentId = getNextStudentId(data); // Calculate the next student ID
                    setStudentData((prevData) => ({
                        ...prevData,
                        studentId: nextStudentId,
                    }));
    
                    console.log("Next studentId generated:", nextStudentId); // Log the generated studentId
                }
            } catch (error) {
                console.error("Error fetching students:", error);
            }
        };

        // Always fetch students to generate the next studentId
        fetchStudents();
    }, []); // No localStorage dependency

    // Function to calculate the next available studentId
    const getNextStudentId = (students) => {
        if (students.length === 0) return 1; // If no students exist, start from 1

        const maxStudentId = students.reduce((maxId, student) =>
            student.studentId > maxId ? student.studentId : maxId, 0);
        return maxStudentId + 1;
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setStudentData({ ...studentData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const token = localStorage.getItem("jwt_token");
    
        try {
            const response = await fetch("http://localhost:8080/api/students", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(studentData), // Include the auto-generated studentId
            });
    
            if (!response.ok) {
                throw new Error("Failed to create student profile");
            }
    
            const newStudent = await response.json();
            console.log("New student created:", newStudent);
    
            // No need to store the studentId in local storage, handle the session dynamically
            setIsProfileComplete(true); // Mark profile as complete
    
        } catch (error) {
            setErrorMessage(error.message);
            console.error("Error creating student profile:", error);
        }
    };

    if (isProfileComplete) {
        console.log("Student ID after profile creation:", studentData.studentId); // Log the studentId after profile creation
        return <Enrollment studentId={studentData.studentId} />; // Pass the studentId to Enrollment component
    }

    return (
        <div className="student-profile-container">
            <h1>Complete Your Student Profile</h1>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="firstName"
                    placeholder="First Name"
                    value={studentData.firstName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="lastName"
                    placeholder="Last Name"
                    value={studentData.lastName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={studentData.email}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="phone"
                    placeholder="Phone"
                    value={studentData.phone}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="major"
                    placeholder="Major"
                    value={studentData.major}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="year"
                    placeholder="Year"
                    value={studentData.year}
                    onChange={handleChange}
                    required
                />
                <input
                    type="number"
                    name="credits"
                    placeholder="Credits"
                    value={studentData.credits}
                    onChange={handleChange}
                    required
                />
                <button type="submit">Create Profile</button>
            </form>
        </div>
    );
}

export default StudentForm;
