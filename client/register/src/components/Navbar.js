import {Link} from "react-router-dom";

function Navbar() {
    return (
        <nav>
            <Link to={`/`}>Home</Link>
            <Link to={`/courses`}>All Courses</Link>
            <Link to={`/courses/:userId`}>Your Courses</Link>
            <Link to={`/course/add`}>Create A New Course</Link>
            <Link to={`/section/add`}></Link>
        </nav>
    );
}

export default Navbar;