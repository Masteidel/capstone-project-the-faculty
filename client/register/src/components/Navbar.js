import { Link } from "react-router-dom";
import './Navbar.css'; 

function Navbar() {
  return (
    <nav className="navbar">
      <Link className="nav-link" to="/">Home</Link>
      <Link className="nav-link" to="/courses">All Courses</Link>
      <Link className="nav-link" to="/courses/:userId">Your Courses</Link>
      <Link className="nav-link" to="/course/add">Create A New Course</Link>
      <Link className="nav-link" to="/section/add">Add Section</Link>
    </nav>
  );
}

export default Navbar;
