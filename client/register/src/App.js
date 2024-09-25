import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Intro from "./components/Intro";
import Signup from "./components/Signup";
import Login from "./components/Login";
import CourseList from "./components/CourseList";
import CourseSections from "./CourseSections";
import Home from "./components/Home";


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Intro />}></Route>
        <Route path="/home" element={<Home />}></Route>
        <Route path="/signup" element={<Signup />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/courses" element={<CourseList />}></Route>
        {/*<Route path="/course/:courseId" element={<Course />}></Route>*/}
        <Route path="/course/:courseId/sections" element={<CourseSections />} />
      </Routes>
    </Router>
  );
}

export default App;
