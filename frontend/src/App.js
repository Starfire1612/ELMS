import {
  Route,
  Routes,
  Navigate,
  useNavigate,
  useLocation,
} from "react-router-dom";
import "./App.css";
import ForgotPassword from "./component/auth/ForgotPassword";
import SignIn from "./component/auth/SignIn";
import SignUp from "./component/auth/SignUp";
import { useEffect, useState } from "react";
import axios from "axios";
import Profile from "./component/profile/Profile";
import AddCourse from "./component/instructor/AddCourse";
import ManageCourse from "./component/instructor/manage-course/ManageCourse";
import InstructorDashboard from "./component/instructor/InstructorDashboard";
import Setup from "./component/instructor/manage-course/Setup";
import Film from "./component/instructor/manage-course/Film";
import CourseStructure from "./component/instructor/manage-course/CourseStructure";
import CourseDescription from "./component/instructor/manage-course/CourseDescription";
import Curriculum from "./component/instructor/manage-course/Curriculum";
import StudentDashboard from "./component/student/StudentDashboard";
import EditProfile from "./component/profile/EditProfile";
import EditProfilePic from "./component/profile/EditProfilePic";
import EditAccountSecurity from "./component/profile/EditAccountSecurity";
import EditBankAccountDetails from "./component/profile/EditBankAccountDetails";
import StudentExploreCourses from "./component/student/StudentExploreCourses";
import StudentMyLearning from "./component/student/StudentMyLearning";
import SearchedCourses from "./component/student/SearchedCourses";
import CourseDetailsPage from "./component/student/Components/CouseDetails.js";
import CoursePlayer from "./component/course-player/CoursePlayer";

function App() {
  const [loggedInStatus, setLoggedInStatus] = useState(false);
  const [fetchUserFlag, setFetchUserFlag] = useState(true);
  const [userData, setUserData] = useState({});
  const navigate = useNavigate();
  const location = useLocation();

  const reFetchUser = () => {
    setFetchUserFlag(!fetchUserFlag);
  };

  const greetUser = async () => {
    await axios
      .get("http://localhost:8080/greetings", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("userToken")}`,
        },
      })
      .then((response) => response.data)
      .then((data) => {
        setUserData(data);
        setLoggedInStatus(true);
      })
      .catch((err) => console.log(err));
  };

  //check whether the person has loggedin within last 24hrs
  //modification required                                         high priority
  useEffect(() => {
    if (localStorage.getItem("userToken") && localStorage.getItem("userType")) {
      greetUser();
      // if (userData) navigate(location.pathname);
      if (userData) navigate("/");
    }
  }, [fetchUserFlag]);

  const handleLogin = async () => {
    await greetUser();
    if (userData) navigate("/");
  };

  const handleLogout = () => {
    localStorage.clear();
    setUserData({});
    setLoggedInStatus(false);
    navigate("/");
  };

  return (
    <div className="App">
      <Routes>
        <Route
          path="/"
          exact
          element={
            loggedInStatus ? (
              localStorage.getItem("userType") === "student" ? (
                <Navigate replace to="/home" />
              ) : (
                <Navigate replace to="/instructor" />
              )
            ) : (
              <SignIn handleLogin={handleLogin} />
            )
          }
        />
        {/* <Route path="/payment" element={<Payment userData={userData}/>} /> */}
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route
          path="/home"
          element={
            <StudentDashboard handleLogout={handleLogout} userData={userData} />
          }
        >
          <Route
            path="explore"
            element={<StudentExploreCourses userData={userData} />}
          />
          <Route
            path="my-learning"
            element={<StudentMyLearning userData={userData} />}
          />
          <Route
            path="searchedCourses/:search"
            element={<SearchedCourses userData={userData} />}
          />

          <Route path="" element={<Navigate to="/home/explore" />} />
        </Route>
        <Route
          path="/student/:studentId/course/:courseId/lesson/"
          element={<CoursePlayer userData={userData} />}
        />
        {/* <Route
          path="/home/course/:courseId"
          element={<CourseViewPage userData={userData} />}
        /> */}
        <Route
          path="/home/course/:courseId"
          element={<CourseDetailsPage userData={userData} />}
        />

        {/* Profile Routes */}
        <Route
          path="/profile"
          exact
          element={<Profile userData={userData} reFetchUser={reFetchUser} />}
        >
          <Route
            path="edit-profile"
            element={
              <EditProfile userData={userData} reFetchUser={reFetchUser} />
            }
          ></Route>
          <Route
            path="edit-profile-pic"
            element={
              <EditProfilePic userData={userData} reFetchUser={reFetchUser} />
            }
          />
          <Route
            path="edit-account-security"
            element={
              <EditAccountSecurity
                userData={userData}
                reFetchUser={reFetchUser}
              />
            }
          />
          <Route
            path="edit-bank-account-details"
            element={
              <EditBankAccountDetails
                userData={userData}
                reFetchUser={reFetchUser}
              />
            }
          />
          <Route path="" element={<Navigate to="/profile/edit-profile" />} />
          <Route path="*" element={<Navigate to="/profile/edit-profile" />} />
        </Route>
        <Route
          path="/instructor"
          element={
            <InstructorDashboard
              handleLogout={handleLogout}
              userData={userData}
            />
          }
        />
        <Route
          path="/instructor/add-course"
          element={<AddCourse userData={userData} />}
        />
        <Route
          path="/instructor/course/:courseId/manage"
          element={<ManageCourse />}
        >
          <Route path="course-structure" element={<CourseStructure />} />
          <Route path="setup" element={<Setup />} />
          <Route path="film" element={<Film />} />
          <Route
            path="description"
            element={<CourseDescription userData={userData} />}
          />
          <Route
            path="curriculum"
            element={<Curriculum userData={userData} />}
          />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
