import { Link } from "react-router-dom";
import './Navbar.css'; 

function Navbar() {
  return (
    <nav className="navbar">
      <Link className="nav-link" to="/">Home</Link>
      <Link className="nav-link" to="/courses">All Courses</Link>
      <Link className="nav-link" to="/enrollment">Enrollment Options</Link>
      <Link className="nav-link" to="/course/add">Create A New Course</Link>
      <Link className="nav-link" to="/sections">Sections</Link>
    </nav>
  );
}

export default Navbar;
