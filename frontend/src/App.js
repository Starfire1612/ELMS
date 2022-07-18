import { Route, Routes, Navigate } from "react-router-dom";
import "./App.css";
import ForgotPassword from "./component/auth/ForgotPassword";
import SignIn from "./component/auth/SignIn";
import SignUp from "./component/auth/SignUp";
import { useState } from "react";
import axios from "axios";
import HomePage from "./component/HomePage";
import Profile from "./component/Profile";
import Courses from "./component/Courses";
import AddCourse from "./component/instructor/AddCourse";
import ManageCourse from "./component/instructor/manage-course/ManageCourse";
import InstructorDashboard from "./component/instructor/InstructorDashboard";
import Setup from "./component/instructor/manage-course/Setup";
import Film from "./component/instructor/manage-course/Film";
import CourseStructure from "./component/instructor/manage-course/CourseStructure";
import CourseDescription from "./component/instructor/manage-course/CourseDescription";
import Curriculum from "./component/instructor/manage-course/Curriculum";

function App(props) {
  const [loggedInStatus, setLoggedInStatus] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [userData, setUserData] = useState({});

  const handleLogin = async () => {
    setIsLoading(true);
    const data = await axios
      .get("http://localhost:8080/greetings", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("userToken")}`,
        },
      })
      .then((response) => response.data)
      .catch((err) => console.log(err));
    setUserData(data);
    setLoggedInStatus(true);
    setIsLoading(false);
  };
  const handleLogout = () => {
    localStorage.removeItem("userToken");
    localStorage.removeItem("userType");
    setUserData({});
    setLoggedInStatus(false);
  };

  return (
    <div className="App">
      <Routes>
        <Route
          path="/"
          exact
          element={
            loggedInStatus ? (
              <Navigate replace to="/home" />
            ) : (
              <SignIn handleLogin={handleLogin} />
            )
          }
        />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route
          path="/home"
          element={<HomePage onLogOut={handleLogout} userData={userData} />}
        />
        {/* <Route path="/home/courses" element={<Courses />} /> */}
        <Route path="profile" element={<Profile />} />
        <Route
          path="/instructor"
          element={
            <InstructorDashboard onLogOut={handleLogout} userData={userData} />
          }
        />
        <Route path="/instructor/add-course" element={<AddCourse />} />
        <Route
          path="/instructor/course/:courseId/manage"
          element={<ManageCourse />}
        >
          <Route path="course-structure" element={<CourseStructure />} />
          <Route path="setup" element={<Setup />} />
          <Route path="film" element={<Film />} />
          <Route path="description" element={<CourseDescription />} />
          <Route path="curriculum" element={<Curriculum />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
