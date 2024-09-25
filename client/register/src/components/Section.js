import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";

function Section() {

    const [section, setSection] = useState([]);
    const [lectures, setLectures] = useState([]);

    const baseUrl = 'http://localhost:8080/api';
    const sectionUrl = `${baseUrl}/section`;
    const lecturesUrl = `${baseUrl}/lectures`;
    const {sectionId} = useParams();

    useEffect(() => {
        if (sectionId) {
            fetch(`${sectionUrl}/${sectionId}`)
                .then(res => {
                    if (res.status === 200) {
                        return res.json();
                    } else {
                        return Promise.reject(`Unexpected Status Code: ${res.status}`);
                    }
                })
                .then(data => {
                    setSection(data);
                })
                .catch(console.log);

            fetch(`${lecturesUrl}`)
                .then(res => {
                    if (res.status === 200) {
                        return res.json();
                    } else {
                        return Promise.reject(`Unexpected Status Code: ${res.status}`);
                    }
                })
                .then(data => {
                    setLectures(data);
                })
                .catch(console.log);
        }
    }, [sectionId]);

    const handleDeleteLecture = (lectureId) => {
        if (window.confirm(`Are you sure you want to delete this lecture?`)) {
            const init = {
                method: 'DELETE',
            };

            fetch(`${lecturesUrl}/${lectureId}`, init)
                .then(res => {
                    if (res.status === 204) {
                        const newLectures = lectures.filter(lecture => lecture.lectureId !== lectureId);
                        setLectures(newLectures);
                    } else {
                        return Promise.reject(`Unexpected Status Code: ${res.status}`);
                    }
                })
        }
    }

    return (
        <section className="container">
            <h2 className="mb-4">Lecture Times</h2>
            <Link to={`/lecture/add/${courseId}`}>
                Add Lecture
            </Link>
            <table className="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>Day of the Week</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    {lectures.map(lecture => (
                        <tr key={lecture.lectureId}>
                            <td>{lecture.day}</td>
                            <td>{lecture.startTime}</td>
                            <td>{lecture.endTime}</td>
                            {/*TODO: conditionally hide buttons if not the professor teaching the course*/}
                            <td>
                                <Link className="btn btn-outline-warning mr4"
                                      to={`/agent/edit/${lecture.lectureId}`}>
                                    Update
                                </Link>
                                <button className="btn btn-outline-danger"
                                        onClick={() => handleDeleteLecture(lecture.lectureId)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </section>
    );
}

export default Section;