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
      try {
        const decodedToken = jwtDecode(token);
        console.log("Decoded Token:", decodedToken);

        // Assuming decodedToken.authorities might be an array or object
        if (Array.isArray(decodedToken.authorities)) {
          // Check if it contains the required role
          if (decodedToken.authorities.includes('ROLE_STUDENT')) {
            setUserRole('ROLE_STUDENT');
          } else if (decodedToken.authorities.includes('ROLE_PROFESSOR')) {
            setUserRole('ROLE_PROFESSOR');
          }
        } else {
          // Fallback to string comparison if it's not an array
          setUserRole(decodedToken.authorities);
        }
      } catch (error) {
        console.error("Failed to decode token", error);
        setUserRole(null); // Clear role on error
      }
    } else {
      setUserRole(null); // Clear role if no token is found
    }
  };

  // Use effect to check the token periodically
  useEffect(() => {
    // Set an interval to refresh the token every second (1000 ms)
    const interval = setInterval(() => {
      updateUserRole(); // Call the function to update the role periodically
    }, 1000); // Poll every second (adjust as needed)

    // Cleanup the interval when the component is unmounted
    return () => clearInterval(interval);
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
