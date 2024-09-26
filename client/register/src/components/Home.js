import React, { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";


function Home() {
    const [lectures, setLectures] = useState([]);
    const [courses, setCourses] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");
    const [userRole, setUserRole] = useState(null);
    const [userId, setUserId] = useState(null);

    // Extract user role and ID from JWT token
    const getUserInfoFromToken = () => {
        const token = localStorage.getItem("jwt_token");
        if (token) {
            const decodedToken = jwtDecode(token);
            return {
                role: decodedToken.authorities, // Assuming 'authorities' is an array containing the role
            };
        }
        return null;
    };

    // Fetch user role and ID from token on component mount
    useEffect(() => {
        const userInfo = getUserInfoFromToken();
        if (userInfo) {
            setUserRole(userInfo.role);
            setUserId(userInfo.userId);
        }
    }, []);

    // Fetch lectures for professors or courses for students based on role
    useEffect(() => {
        const fetchData = async () => {
            const token = localStorage.getItem("jwt_token");

            if (!token) {
                setErrorMessage("No token found. Please log in.");
                return;
            }

            try {
                let url;
                if (userRole && userRole.includes("ROLE_PROFESSOR")) {
                    url = `http://localhost:8080/api/lectures/professor/${userId}`;
                } else if (userRole && userRole.includes("ROLE_STUDENT")) {
                    url = `http://localhost:8080/api/courses/student/${userId}`;
                }

                if (url) {
                    const response = await fetch(url, {
                        method: "GET",
                        headers: {
                            Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
                        },
                    });

                    const data = await response.json();
                    console.log("Fetched data:", data);

                    if (response.ok && data.payload) {
                        if (userRole.includes("ROLE_PROFESSOR")) {
                            setLectures(data.payload); // Assuming 'payload' contains lectures for professors
                        } else if (userRole.includes("ROLE_STUDENT")) {
                            setCourses(data.payload); // Assuming 'payload' contains courses for students
                        }
                    } else {
                        setErrorMessage("No data available.");
                    }
                }
            } catch (error) {
                setErrorMessage(error.message);
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, [userRole, userId]);

    return (
        <div className="container">
            <h1>Dashboard</h1>
            {errorMessage && <p className="error-message">{errorMessage}</p>}

            {/* Conditionally render content based on the user role */}
            {userRole && userRole.includes("ROLE_PROFESSOR") && (
                <div>
                    <h2>Your Lectures</h2>
                    {lectures.length > 0 ? (
                        <table className="table table-striped">
                            <thead>
                                <tr>
                                    <th>Lecture ID</th>
                                    <th>Day</th>
                                    <th>Start Time</th>
                                    <th>End Time</th>
                                </tr>
                            </thead>
                            <tbody>
                                {lectures.map((lecture) => (
                                    <tr key={lecture.lectureId}>
                                        <td>{lecture.lectureId}</td>
                                        <td>{lecture.day}</td>
                                        <td>{lecture.startTime}</td>
                                        <td>{lecture.endTime}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : (
                        <p>No lectures available.</p>
                    )}
                </div>
            )}

            {userRole && userRole.includes("ROLE_STUDENT") && (
                <div>
                    <h2>Your Courses</h2>
                    {courses.length > 0 ? (
                        <table className="table table-striped">
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
                        <p>No courses available.</p>
                    )}
                </div>
            )}
        </div>
    );
}

export default Home;