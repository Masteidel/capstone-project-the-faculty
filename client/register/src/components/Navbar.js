import { Link } from "react-router-dom";
import './Navbar.css'; 
import { useEffect, useState } from 'react'; 
import {jwtDecode} from 'jwt-decode'; 

function Navbar() {
  const [userRole, setUserRole] = useState(null);

  // Function to update user role
  const updateUserRole = () => {
    const token = localStorage.getItem("jwt_token");
    if (token) {
      const decodedToken = jwtDecode(token);
      console.log("Decoded Token:", decodedToken);

      // Set the user role based on the decoded token
      setUserRole(decodedToken.authorities);
    } else {
      setUserRole(null); // Reset role if no token is found
    }
  };

  // Use effect to decode the token and extract the user's role
  useEffect(() => {
    updateUserRole(); // Call the function to set the role on component mount

    // Add an event listener for changes in localStorage
    window.addEventListener('storage', updateUserRole);

    // Cleanup the event listener when the component is unmounted
    return () => {
      window.removeEventListener('storage', updateUserRole);
    };
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
