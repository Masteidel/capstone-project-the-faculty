import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Intro from "./components/Intro";
import Signup from "./components/Signup";
import Login from "./components/Login";
import CourseList from "./components/CourseList";
import Layout from './components/Layout'; // Import the Layout component
import ProfessorLogin from "./components/ProfessorLogin"; // Import Professor Login component
import StudentLogin from "./components/StudentLogin"; // Import Student Login component

function App() {
  return (
    <Router>
      <Routes>
        {/* Use the Layout component to wrap your routes */}
        <Route path="/" element={<Layout />}>
          <Route index element={<Intro />} />  {/* Default route */}
          <Route path="signup" element={<Signup />} />
          <Route path="login" element={<Login />} />
          <Route path="login/professor" element={<ProfessorLogin />} /> {/* Professor Login Route */}
          <Route path="login/student" element={<StudentLogin />} /> {/* Student Login Route */}
          <Route path="courses" element={<CourseList />} />
          {/* Add more routes as necessary */}
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
