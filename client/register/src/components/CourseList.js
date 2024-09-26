import React, { useEffect, useState } from "react";
import "./CourseList.css";
import { jwtDecode } from 'jwt-decode'; // Correct way to use named import
// Correct import

function CourseList() {
    const [courses, setCourses] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");
    const [newCourse, setNewCourse] = useState({
        courseId: 0,
        name: "",
        subject: "",
        credits: 0,
      });
    const [userRole, setUserRole] = useState(null);

    // Function to extract the user role from the JWT token
    const getUserRole = () => {
        const token = localStorage.getItem("jwt_token");
        if (token) {
            const decodedToken = jwtDecode(token); // Use jwtDecode here
            return decodedToken.authorities; // Assuming 'authorities' contains the role
        }
        return null;
    };

    // Fetch the user role when the component is mounted
    useEffect(() => {
        const role = getUserRole();
        setUserRole(role); // Store the role in the state
    }, []);

    // Fetch courses when the component is mounted
    useEffect(() => {
        const fetchCourses = async () => {
            const token = localStorage.getItem("jwt_token");

            if (!token) {
                setErrorMessage("No token found. Please log in.");
                return;
            }

            try {
                const response = await fetch("http://localhost:8080/api/courses", {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });

                const data = await response.json(); // Parse the JSON response
                console.log("Courses data:", data); // Log the response to check the data

                // Access the `payload` where the courses array is located
                if (!response.ok || !data.payload || data.payload.length === 0) {
                    setErrorMessage("No courses available.");
                } else {
                    setCourses(data.payload); // Set the fetched courses to state from the payload
                }

            } catch (error) {
                setErrorMessage(error.message);
                console.error("Error fetching courses:", error);
            }
        };

        fetchCourses(); // Call the function to fetch courses
    }, []);


    // Add a new course
    const handleAddCourse = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem("jwt_token");
    
        try {
            const response = await fetch("http://localhost:8080/api/courses", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    courseId: newCourse.courseId, // Include courseId
                    name: newCourse.name,
                    subject: newCourse.subject,
                    credits: newCourse.credits,
                }),
            });
    
            const data = await response.json();
            console.log("Response data:", data);
    
            // Check if the response contains valid course data
            if (data && data.courseId && data.name && data.subject && data.credits) {
                setCourses([...courses, data]); // Append the new course to the list
                setNewCourse({ courseId: 0, name: "", subject: "", credits: 0 }); // Reset the form
            } else {
                throw new Error("Invalid course data returned from the server.");
            }
    
        } catch (error) {
            setErrorMessage(error.message);
            console.error("Error adding course:", error);
        }
    };


    return (
        <div className="course-list">
            <h1>Course list</h1>
            {errorMessage && <p className="error-message">{errorMessage}</p>}

            {/* Conditionally render Add Course form for professors */}
            {userRole && userRole.includes("ROLE_PROFESSOR") && (
                <form onSubmit={handleAddCourse} className="add-course-form">
                <h2>Add New Course</h2>
                <input
                    type="number"
                    placeholder="Course ID"
                    value={newCourse.courseId}
                    onChange={(e) => setNewCourse({ ...newCourse, courseId: parseInt(e.target.value) })}
                    required
                />
                <input
                    type="text"
                    placeholder="Course Name"
                    value={newCourse.name}
                    onChange={(e) => setNewCourse({ ...newCourse, name: e.target.value })}
                    required
                />
                <input
                    type="text"
                    placeholder="Subject"
                    value={newCourse.subject}
                    onChange={(e) => setNewCourse({ ...newCourse, subject: e.target.value })}
                    required
                />
                <input
                    type="number"
                    placeholder="Credits"
                    value={newCourse.credits}
                    onChange={(e) => setNewCourse({ ...newCourse, credits: parseInt(e.target.value) })}
                    required
                />
                <button type="submit">Add Course</button>
            </form>
            )}

            {courses.length > 0 ? (
                <table className="course-table">
                    <thead>
                        <tr>
                            <th>Course ID</th>
                            <th>Course Name</th>
                            <th>Credits</th>
                            <th>Subject</th>
                            
                        </tr>
                    </thead>
                    <tbody>
                        {courses.map((course) => (
                            <tr key={course.courseId}>
                                <td>{course.courseId}</td>
                                <td>{course.name}</td>
                                <td>{course.credits}</td>
                                <td>{course.subject}</td>
                               
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>No courses available at the moment.</p>
            )}
        </div>
    );
}

export default CourseList;
