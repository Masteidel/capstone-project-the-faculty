import React from "react";

function Signup() {

    const [accountType, setAccountType] = React.useState('');

    const handleAccountTypeChange = (event) => {
        setAccountType(event.target.value);
    }

    return (
        <div className="container d-flex justify-content-center align-items-center vh-100">
            <div className="card shadow-sm" style={{ width: '400px' }}>
                <div className="card-body">
                    <h3 className="text-center mb-4">Sign Up</h3>
                    <form>
                        <fieldset className="form-group">
                            <label htmlFor="firstName">First Name</label>
                            <input
                                id="firstName"
                                name="firstName"
                                type="text"
                                className="form-control"
                                placeholder="Enter your first name"
                            />
                        </fieldset>
                        <fieldset className="form-group mt-3">
                            <label htmlFor="lastName">Last Name</label>
                            <input
                                id="lastName"
                                name="lastName"
                                type="text"
                                className="form-control"
                                placeholder="Enter your last name"
                            />
                        </fieldset>
                        <fieldset className="form-group mt-3">
                            <label htmlFor="email">Email</label>
                            <input
                                id="email"
                                name="email"
                                type="email"
                                className="form-control"
                                placeholder="Enter your email"
                            />
                        </fieldset>
                        <fieldset className="form-group mt-3">
                            <label htmlFor="phone">Phone</label>
                            <input
                                id="phone"
                                name="phone"
                                type="tel"
                                className="form-control"
                                placeholder="Enter your phone number"
                            />
                        </fieldset>
                        <fieldset className="form-group mt-3">
                            <label htmlFor="password">Password</label>
                            <input
                                id="password"
                                name="password"
                                type="password"
                                className="form-control"
                                placeholder="Enter your password"
                            />
                        </fieldset>
                        <fieldset className="form-group mt-3">
                            <label htmlFor="confirmPassword">Confirm Password</label>
                            <input
                                id="confirmPassword"
                                name="confirmPassword"
                                type="password"
                                className="form-control"
                                placeholder="Confirm your password"
                            />
                        </fieldset>
                        {accountType === 'professor' && (
                            <fieldset className="form-group mt-3">
                                <label htmlFor="accessCode">Access Code</label>
                                <input
                                    id="accessCode"
                                    name="accessCode"
                                    type="text"
                                    className="form-control"
                                    placeholder="Enter access code"
                                />
                            </fieldset>
                        )}
                        <fieldset className="form-group mt-4">
                            <legend>Account Type</legend>
                            <div className="form-check">
                                <input
                                    type="radio"
                                    id="professor"
                                    name="role"
                                    value="professor"
                                    className="form-check-input"
                                    onChange={handleAccountTypeChange}
                                />
                                <label htmlFor="professor" className="form-check-label">Professor</label>
                            </div>
                            <div className="form-check">
                                <input
                                    type="radio"
                                    id="student"
                                    name="role"
                                    value="student"
                                    className="form-check-input"
                                    onChange={handleAccountTypeChange}
                                />
                                <label htmlFor="student" className="form-check-label">Student</label>
                            </div>
                        </fieldset>
                        <div className="mt-4 d-grid">
                            <button type="submit" className="btn btn-dark btn-block">
                                Sign Up
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default Signup;