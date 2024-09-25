import React from 'react';

function Login() {
    return (
        <div className="container d-flex justify-content-center align-items-center vh-100">
            <div className="card shadow-sm" style={{ width: '400px' }}>
                <div className="card-body">
                    <h3 className="text-center mb-4">Login</h3>
                    <form>
                        <fieldset className="form-group">
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
                            <label htmlFor="password">Password</label>
                            <input
                                id="password"
                                name="password"
                                type="password"
                                className="form-control"
                                placeholder="Enter your password"
                            />
                        </fieldset>
                        <div className="mt-4 d-grid">
                            <button type="submit" className="btn btn-dark btn-block">
                                Log In
                            </button>
                        </div>
                        <div className="mt-3 text-center">
                            <a href="#" className="text-decoration-none">Forgot Password?</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default Login;