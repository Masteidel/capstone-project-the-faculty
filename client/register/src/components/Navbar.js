import { Link } from "react-router-dom";
import './Navbar.css'; 
import { useEffect, useState } from 'react'; 
import {jwtDecode } from 'jwt-decode'; 

function Navbar() {
  const [userRole, setUserRole] = useState(null);

  // Use effect to decode the token and extract the user's role
  useEffect(() => {
    const token = localStorage.getItem("jwt_token"); // Get the token from localStorage (or wherever it's stored)
    if (token) {
      const decodedToken = jwtDecode(token); // Decode the token
      console.log("Decoded Token:", decodedToken); // Log the decoded token to check the structure

      // Set the user role based on the "authorities" field
      setUserRole(decodedToken.authorities); // Use the authorities field to get the role
    }
  }, []);

  return (
    <nav className="navbar">
      <Link className="nav-link" to="/">Home</Link>
      <Link className="nav-link" to="/courses">All Courses</Link>

      {/* Only students (ROLE_STUDENT) should see "Enrollment Options" */}
      {userRole === 'ROLE_STUDENT' && (
        <Link className="nav-link" to="/enrollment">Enrollment Options</Link>
      )}

      {/* Only professors (ROLE_PROFESSOR) should see "Create A New Course" and "Sections" */}
      {userRole === 'ROLE_PROFESSOR' && (
        <>
          <Link className="nav-link" to="/studentsEnrolled">Students Enrolled</Link>
          <Link className="nav-link" to="/sections">Sections</Link>
        </>
      )}
    </nav>
  );
}

export default Navbar;
