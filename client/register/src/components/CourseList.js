import {Link} from "react-router-dom";
import {useEffect, useState} from "react";

function CourseList() {

    // State Variables
    const [courses, setCourses] = useState([]);

    const url = 'http://localhost:8080/api/register';

    useEffect(() => {
        fetch(url)
            .then(res => {
                if (res.status === 200) {
                    return res.json();
                } else {
                    return Promise.reject(`Unexpected Status Code: ${res.status}`);
                }
            })
            .then(data => {
                setCourses(data)
            })
            .catch(console.log);
    }, []);

    return (
        <section>
            <h2 className="mb-4">Courses</h2>
            <Link className="btn btn-outline-success mb-4 ml-2" to={`/course/add`}>
                Add Course
            </Link>
            <table className="table table-striped table-hover">
                <thead className="table-dark">
                    <tr>
                        <th>Name</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    {courses.map(course => (
                        <tr key={course.courseId}>
                            <td>{course.name}</td>
                            <td>
                                <Link className="btn btn-outline-primary mr4" to={`/course/${course.courseId}`}>
                                    View
                                </Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </section>
    );
}

export default CourseList;