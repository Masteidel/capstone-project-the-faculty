import React, { useState, useEffect } from "react";
import "./ProfessorForm.css";
import { useNavigate } from "react-router-dom";

function ProfessorForm() {
    const [professorData, setProfessorData] = useState({
        professorId: "", // Auto-generate this value based on existing professors
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
    });
    const [professors, setProfessors] = useState([]); // Store the list of professors
    const [isProfileComplete, setIsProfileComplete] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    // Fetch all professors and find the next available professorId
    useEffect(() => {
        const fetchProfessors = async () => {
            const token = localStorage.getItem("jwt_token");

            try {
                const response = await fetch("http://localhost:8080/api/professors", {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });

                const data = await response.json();
                if (response.ok) {
                    setProfessors(data); // Store the list of professors
                    const nextProfessorId = getNextProfessorId(data); // Calculate the next professor ID
                    setProfessorData((prevData) => ({
                        ...prevData,
                        professorId: nextProfessorId,
                    }));
                    console.log("Next professorId generated:", nextProfessorId); // Log the generated professorId
                }
            } catch (error) {
                console.error("Error fetching professors:", error);
            }
        };

        fetchProfessors();
    }, []);

    // Function to calculate the next available professorId
    const getNextProfessorId = (professors) => {
        if (professors.length === 0) return 1; // If no professors exist, start from 1

        const maxProfessorId = professors.reduce((maxId, professor) =>
            professor.professorId > maxId ? professor.professorId : maxId, 0);
        return maxProfessorId + 1;
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProfessorData({ ...professorData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const token = localStorage.getItem("jwt_token");

        try {
            const response = await fetch("http://localhost:8080/api/professors", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(professorData), // Include the auto-generated professorId
            });

            if (!response.ok) {
                throw new Error("Failed to create professor profile");
            }

            const newProfessor = await response.json();
            console.log("New professor created:", newProfessor);

            alert("Professor profile created successfully!");
            
            setIsProfileComplete(true);
            navigate("/courses"); // Mark profile as complete

        } catch (error) {
            setErrorMessage(error.message);
            console.error("Error creating professor profile:", error);
        }
    };


    return (
        <div className="professor-profile-container">
            <h1>Complete Your Professor Profile</h1>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="firstName"
                    placeholder="First Name"
                    value={professorData.firstName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="lastName"
                    placeholder="Last Name"
                    value={professorData.lastName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={professorData.email}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="phone"
                    placeholder="Phone"
                    value={professorData.phone}
                    onChange={handleChange}
                    required
                />
                <button type="submit">Create Profile</button>
            </form>
        </div>
    );
}

export default ProfessorForm;
