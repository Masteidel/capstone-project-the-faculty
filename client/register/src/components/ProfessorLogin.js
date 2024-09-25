import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import './ProfessorLogin.css';

function ProfessorLogin() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [secretCode, setSecretCode] = useState(""); // This is still the secret code on the UI
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        const requestData = {
            username: username,
            password: password,
            code: secretCode // The backend expects "code", so we send this as "code"
        };

        // Log the payload before sending it for debugging
        console.log("Request Data:", requestData);

        try {
            const response = await fetch("http://localhost:8080/api/user/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(requestData)
            });

            if (!response.ok) {
                throw new Error("Registration failed. Please check your credentials and secret code.");
            }

            const data = await response.json();

            // Save JWT token in localStorage if returned
            if (data.jwt_token) {
                localStorage.setItem("jwt_token", data.jwt_token);
                console.log("Registration successful, JWT token stored!");

                navigate("/courses");
            }

        } catch (error) {
            setErrorMessage(error.message);
            console.error("Error registering:", error);
        }
    };

    return (
        <div className="login-container">
            <h1>Professor Registration</h1>
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

                {/* Secret Professor Code Input */}
                <div className="form-group">
                    <label htmlFor="secretCode">Secret Professor Code</label>
                    <input
                        type="text"
                        id="secretCode"
                        value={secretCode}
                        onChange={(e) => setSecretCode(e.target.value)}
                        required
                    />
                </div>

                {/* Submit Button */}
                <button type="submit" className="login-btn">Register</button>
            </form>
        </div>
    );
}

export default ProfessorLogin;
