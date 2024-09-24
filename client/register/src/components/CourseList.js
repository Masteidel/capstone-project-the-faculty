import {Link} from "react-router-dom";
import {useState} from "react";

function CourseList() {

    // State Variables
    const [courses, setCourses] = useState([]);

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
                                <button className="btn btn-outline-success">
                                    Enroll
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </section>
    );
}

export default CourseList;