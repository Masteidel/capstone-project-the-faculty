function Login() {

    return (
        <section className="container">
            <form>
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
                    <label htmlFor="password">Password</label>
                    <input
                        id="password"
                        name="password"
                        type="password"
                        className="form-control"
                    />
                </fieldset>
                <div className="mt-4">
                    <button type="submit" className="btn btn-dark">
                        Log In
                    </button>
                </div>
            </form>
        </section>
    );
}

export default Login;