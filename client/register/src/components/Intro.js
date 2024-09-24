import {Link} from "react-router-dom";

function Intro() {
    return (<>
        <Link className="btn btn-dark mr4" to={`/signup`}>Create Account</Link>
        <Link className="btn btn-dark mr4" to={`/login`}>Login to Account</Link>
    </>);
}

export default Intro;