import React, { useEffect, useState } from "react";
import { getSearchedCourses, getStudentEnrolledCourses } from "./../courses/courses-util";
import { ClipLoader } from 'react-spinners';
import { LOADING_COLOR } from "../../utils/constants.js";
import Courses from './../courses/Courses';
import { useParams } from "react-router-dom";
import image3 from "D:/ELMS/frontend/src/static/images/nodatafound.png";


function SearchedCourses({ userData }) {
  const [courses, setCourses] = useState([]);
  const [pageCounter, setpageCounter] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  const [totalPages, setTotalPages] = useState(1);

 const params=useParams();
  //fetch all the courses
  const fetchAllCourses = async () => {
    setIsLoading(true);
    const responseData = await getSearchedCourses(userData.studentId,params.search);
     console.log("Feched Courses" + responseData);
    if(!responseData)
    setCourses([]);
    else
    setCourses(responseData);
    // setTotalPages(responseData.totalPages);
    setIsLoading(false);
  };
  const handlePrevPage = async () => {
    if (pageCounter === 0) return;
    setpageCounter((prev) => prev - 1);
  };

  const handleNextPage = async () => {
    if (pageCounter === totalPages - 1) return;
    setpageCounter((prev) => prev + 1);
  };

  useEffect(() => {
    fetchAllCourses();
  }, [userData, params.search]);

  return (
    <div className="student-dashboard-body">
      <div className="course-list">
        {isLoading ? (
          <div className="loading-courses-list my-5">
            <ClipLoader color={LOADING_COLOR} size="50px" />
          </div>
        ) : (
            courses.length>0 ?
          <Courses courses={courses} userData={userData} />
          :   (<div className="mt-4 ">
            <h2 className="text-center" >No such courses!!</h2>
          <img className="d-block mx-auto mt-2 "  src={image3} alt="Third slide"/>
          </div>)
        )}
      
      </div>
    </div>
  );
}
export default SearchedCourses;
