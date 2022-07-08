import React from "react";
import Courses from "./Courses";
import Navigationbar from "./Navigationbar";

function HomePage({ handleLogout, userData, isLoading }) {
  console.log(userData);
  return (
    <div>
      {!isLoading ? (
        <Navigationbar handleLogout={handleLogout} userData={userData} />
      ) : (
        ""
      )}

      {localStorage.getItem("userType") === "student" ? (
        <Courses />
      ) : (
        "Instructor"
      )}
    </div>
  );
}

export default HomePage;
