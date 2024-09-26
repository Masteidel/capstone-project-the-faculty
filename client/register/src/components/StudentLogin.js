import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import './StudentLogin.css';

function StudentLogin() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        const requestData = {
            username: username,
            password: password
        };

        // Log the payload before sending it for debugging
        console.log("Request Data:", requestData);

        try {
            // Step 1: Attempt to login
            const loginResponse = await fetch("http://localhost:8080/api/user/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(requestData)
            });

            if (loginResponse.ok) {
                // If login is successful, get the JWT token
                const data = await loginResponse.json();
                localStorage.setItem("jwt_token", data.jwt_token);
                console.log("Login successful, JWT token stored!");

                // Navigate to the student's dashboard or form
                navigate("/courses");
                return;
            }

            // Step 2: If login fails, proceed with registration
            const registerResponse = await fetch("http://localhost:8080/api/user/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ ...requestData, code: "" })  // Empty code for student registration
            });

            if (!registerResponse.ok) {
                throw new Error("Registration failed. Please check your credentials.");
            }

            const registerData = await registerResponse.json();

            if (registerData.jwt_token) {
                localStorage.setItem("jwt_token", registerData.jwt_token);
                console.log("Registration successful, JWT token stored!");

                // Navigate to the student's form for profile completion
                navigate("/student-form");
            }

        } catch (error) {
            setErrorMessage(error.message);
            console.error("Error during login/registration:", error);
        }
    };

    return (
        <div className="login-container">
            <h1>Student Login / Registration</h1>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <form className="login-form" onSubmit={handleSubmit}>
                {/* Username Input */}
                <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>

                {/* Password Input */}
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                {/* Submit Button */}
                <button type="submit" className="login-btn">Login / Register</button>
            </form>
        </div>
    );
}

export default StudentLogin;
