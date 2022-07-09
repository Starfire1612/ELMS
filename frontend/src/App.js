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

function App(props) {
  const [loggedInStatus, setLoggedInStatus] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [userData, setUserData] = useState({});

  const handleLogin = async () => {
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
              <Navigate replace to="/elms" />
            ) : (
              <SignIn handleLogin={handleLogin} setIsLoading={setIsLoading} />
            )
          }
        />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route
          path="/elms"
          element={<HomePage onLogOut={handleLogout} userData={userData} />}
        >
          <Route path="profile" element={<Profile />} />
          <Route path="courses" element={<Courses />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
