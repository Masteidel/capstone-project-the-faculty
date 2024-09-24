function Signup() {
    return (
        <section className="container">
            <form>
                <fieldset className="form-group">
                    <label htmlFor="firstName">First Name</label>
                    <input
                        id="firstName"
                        name="firstName"
                        type="text"
                        className="form-control"
                    />
                </fieldset>
                <fieldset className="form-group">
                    <label htmlFor="lastName">First Name</label>
                    <input
                        id="lastName"
                        name="lastName"
                        type="text"
                        className="form-control"
                    />
                </fieldset>
                <fieldset className="form-group">
                    <label htmlFor="email">Email</label>
                    <input
                        id="email"
                        name="email"
                        type="email"
                        className="form-control"
                    />
                </fieldset>
                <fieldset className="form-group">
                    <label htmlFor="phone">Phone</label>
                    <input
                        id="phone"
                        name="phone"
                        type="tel"
                        className="form-control"
                    />
                </fieldset>
                <fieldset className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        id="password"
                        name="password"
                        type="password"
                        className="form-control"
                    />
                </fieldset>
                <fieldset className="form-group">
                    <label htmlFor="confirmPassword">Confirm Password</label>
                    <input
                        id="confirmPassword"
                        name="confirmPassword"
                        type="password"
                        className="form-control"
                    />
                </fieldset>
                <fieldset className="form-group">
                    <legend>Account Type</legend>
                    <div>
                        <input
                            type="radio"
                            id="professor"
                            name="role"
                            value="professor"
                        />
                        <label htmlFor="professor">Professor</label>
                    </div>
                    <div>
                        <input
                            type="radio"
                            id="student"
                            name="role"
                            value="student"
                        />
                        <label htmlFor="student">Student</label>
                    </div>
                </fieldset>
                <div className="mt-4">
                    <button type="submit" className="btn btn-dark">
                        Sign Up
                    </button>
                </div>
            </form>
        </section>
    );
}

export default Signup;