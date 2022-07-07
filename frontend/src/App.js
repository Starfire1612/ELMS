import { Route, Routes } from "react-router-dom";
import "./App.css";
import ForgotPassword from "./component/ForgotPassword";
import SignIn from "./component/SignIn";
import SignUp from "./component/SignUp";
import { useState, useEffect } from "react";
import axios from "axios";
import { BASE_URL } from "./util/constants.js";

function App() {
  // const [loggedInStatus, setLoggedinStatus] = useState(false);
  // const fetchUserData = async () => {
  //   const config = {
  //     url: "http:/localhost:8080/greetings",
  //     header: {
  //       Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  //     },
  //   };
  //   return await axios(config).then((response) => response.data).catch(error => error);
  // };
  // useEffect(() => {
  //   if (loggedInStatus === false) {
  //     <SignIn onLogIn={handleLogin}></SignIn>;
  //   } else {
  //     console.log(fetchUserData());
  //     fetchUserData();
  //   }
  // }, [loggedInStatus]);

  // const handleLogin = () => {
  //   setLoggedinStatus(true);
  //   fetchUserData();
  // };
  return (
    <Routes>
      <Route path="/" element={<SignIn />} />
      <Route path="/sign-up" element={<SignUp />} />
      <Route path="/forgot-password" element={<ForgotPassword />} />
    </Routes>
  );
}

export default App;
