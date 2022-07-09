import React, { useEffect, useState } from "react";
import Courses from "./Courses";
import Navigationbar from "./Navigationbar";
import { dummyCourses } from "./dummydata/dummyCourses";

function HomePage({ handleLogout, userData, isLoading }) {
  const [courses, setCourses] = useState([]);

  //fetch all the courses
  const fetchAllCourses = async () => {
    //fetch the courses using axios and set state
    setCourses(dummyCourses);
  };
  useEffect(() => {
    fetchAllCourses();
  }, []);

  return (
    <div>
      {!isLoading ? (
        <Navigationbar handleLogout={handleLogout} userData={userData} />
      ) : (
        ""
      )}
      <div className="mt-5 p-3">
        {localStorage.getItem("userType") === "student" ? (
          <Courses courses={courses} />
        ) : (
          "Instructor"
        )}
      </div>
    </div>
  );
}

export default HomePage;
