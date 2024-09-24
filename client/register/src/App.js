import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Intro from "./components/Intro";
import Signup from "./components/Signup";
import Login from "./components/Login";


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Intro />}></Route>
        <Route path="/signup" element={<Signup />}></Route>
        <Route path="/login" element={<Login />}></Route>
      </Routes>
    </Router>
  );
}

export default App;
