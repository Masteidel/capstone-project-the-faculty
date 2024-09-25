import React, { useState } from "react";
import './StudentLogin.css';

function StudentLogin() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    // Add your form submission logic here
    console.log({ username, password });
  };

  return (
    <div className="login-container">
      <h1>Student Login</h1>
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
        <button type="submit" className="login-btn">Login</button>
      </form>
    </div>
  );
}

export default StudentLogin;
