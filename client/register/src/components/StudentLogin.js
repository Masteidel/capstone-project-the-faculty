import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import './StudentLogin.css';

function StudentLogin() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [showRegister, setShowRegister] = useState(false); // Toggle between login and register form
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        const requestData = {
            username: username,
            password: password
        };

        try {
            const loginResponse = await fetch("http://localhost:8080/api/user/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(requestData)
            });

            if (!loginResponse.ok) {
                throw new Error("Login failed. Please check your credentials.");
            }

            const data = await loginResponse.json();
            localStorage.setItem("jwt_token", data.jwt_token);
            console.log("Login successful, JWT token stored!");

            // Navigate to the student's dashboard or form
            navigate("/courses");
        } catch (error) {
            setErrorMessage(error.message);
            console.error("Error logging in:", error);
        }
    };

    const handleRegister = async (e) => {
        e.preventDefault();

        const requestData = {
            username: username,
            password: password,
            code: ""  // Empty code for student registration
        };

        try {
            const registerResponse = await fetch("http://localhost:8080/api/user/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(requestData)
            });

            if (!registerResponse.ok) {
                throw new Error("Registration failed. Please check your credentials.");
            }

            const registerData = await registerResponse.json();
            localStorage.setItem("jwt_token", registerData.jwt_token);
            console.log("Registration successful, JWT token stored!");

            // Navigate to the student's form for profile completion
            navigate("/student-form");
        } catch (error) {
            setErrorMessage(error.message);
            console.error("Error registering:", error);
        }
    };

    return (
        <div className="login-container">
            <h1>{showRegister ? "Student Registration" : "Student Login"}</h1>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            {!showRegister ? (
                <form className="login-form" onSubmit={handleLogin}>
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
                    <button type="submit" className="login-btn">Login</button>

                    <p>
                        Don’t have an account?{" "}
                        <button
                            type="button"
                            className="toggle-btn"
                            onClick={() => setShowRegister(true)}
                        >
                            Create New Account
                        </button>
                    </p>
                </form>
            ) : (
                <form className="register-form" onSubmit={handleRegister}>
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
                    <button type="submit" className="register-btn">Register</button>

                    <p>
                        Already have an account?{" "}
                        <button
                            type="button"
                            className="toggle-btn"
                            onClick={() => setShowRegister(false)}
                        >
                            Back to Login
                        </button>
                    </p>
                </form>
            )}
        </div>
    );
}

export default StudentLogin;
