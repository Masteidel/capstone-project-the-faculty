import React from "react";
import ProfessorsWithLectures from "./ProfessorsWithLectures";
import StudentsWithCourses from "./StudentsWithCourses";

function Home() {
    return (
        <div className="container">
            <header>
                <h1 className="text-center">Welcome to the Faculty Dashboard</h1>
            </header>
            <div className="row">
                <div className="col-md-6">
                    <ProfessorsWithLectures />
                </div>
                <div className="col-md-6">
                    <StudentsWithCourses />
                </div>
            </div>
            <footer className="text-center mt-5">
                <p>&copy; 2024 All Rights Reserved</p>
            </footer>
        </div>
    );
}

export default Home;