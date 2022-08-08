import { useEffect, useState } from "react";
import { Button, Pagination } from "react-bootstrap";
import { CaretLeft, CaretRight } from "react-bootstrap-icons";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants";
import Courses from "../courses/Courses";
import { getAllPublishedCourse } from "../courses/courses-util";
import DashboardCarousel from "./DashboardCarousel";

function StudentExploreCourses({ userData }) {
  const [courses, setCourses] = useState([]);
  const [pageCounter, setpageCounter] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  const [totalPages, setTotalPages] = useState(1);
  //fetch all the courses
  const fetchAllCourses = async () => {
    console.log(localStorage.getItem("userToken"));
    setIsLoading(true);
 
    const responseData = await getAllPublishedCourse(
      userData.studentId,
      pageCounter
    );
    if (!responseData) {
      setIsLoading(false);
      return;
    } else {
      setCourses(responseData.content);
      setTotalPages(responseData.totalPages);
    }

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
  }, [userData, pageCounter]);

  return (
    <div className="student-dashboard-body">
      {!isLoading&&userData ? (
        <>
          <div className="carousel-container mb-5">
            <DashboardCarousel />
          </div>
          <div className="explore-dashboard text-light d-table">
            <p className="my-auto d-table-cell fs-3 fw-500 font-monospace">
              Explore...
            </p>
          </div>
          <div className="course-list">
            {isLoading && !courses? (
              <div className="loading-courses-list my-5">
                <ClipLoader className="d-block mx-auto my-auto align-items-center justify-content-center" color={LOADING_COLOR} size="50px" />
              </div>
            ) : (
              <Courses courses={courses} userData={userData} />
            )}
            <div className="mt-4">
              <Pagination className="justify-content-center" size="lg">
                <Button
                  className="me-5 type-3 pagination-button"
                  onClick={handlePrevPage}
                  disabled={pageCounter === 0}
                >
                  <CaretLeft size={30} />
                </Button>
                <Button
                  className="type-3 pagination-button"
                  onClick={handleNextPage}
                  disabled={pageCounter === totalPages - 1}
                >
                  <CaretRight size={30} />
                </Button>
              </Pagination>
            </div>
          </div>
        </>
      ) : (
        <ClipLoader className="d-block mx-auto my-auto align-items-center justify-content-center" color={LOADING_COLOR} size="50px" />
      )}
    </div>
  );
}

export default StudentExploreCourses;
