import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../../utils/constants";
import { calculateDiscountedPrice, formatter } from "../../../utils/util";
import {
  getAllCourseEnrolledStudents,
  getMonthlyCourseReveneue,
} from "../instructor-utils";

export default function CourseReportsCards({ course, userData }) {
  const params = useParams();
  const courseId = params.courseId;
  const [totalStudentEnrolled, setTotalStudentEnrolled] = useState();
  const [monthlyRevenue, setMonthlyRevenue] = useState();
  const [isLoadingForMonthlyRevenue, setIsLoadingForMonthlyRevenue] =
    useState(true);
  const [isLoadingForTotalStudent, setIsLoadingForTotalStudent] =
    useState(true);
  const clipLoader = <ClipLoader color={LOADING_COLOR} size="35px" />;

  const fetchTotalStudentsOfCourse = async () => {
    setIsLoadingForTotalStudent(true);
    const response = await getAllCourseEnrolledStudents(
      userData.instructorId,
      courseId
    );
    setTotalStudentEnrolled(response);
    console.log("Total student registered in ", courseId, "are:", response);
    setIsLoadingForTotalStudent(false);
  };

  const fetchCourseMonthlyEarnings = async () => {
    setIsLoadingForMonthlyRevenue(true);
    const response = await getMonthlyCourseReveneue(
      userData.instructorId,
      courseId
    );
    console.log("Monthly earnings of ", courseId, "is:", response);
    setMonthlyRevenue(response);
    setIsLoadingForMonthlyRevenue(false);
  };

  useEffect(() => {
    fetchTotalStudentsOfCourse();
    fetchCourseMonthlyEarnings();
  }, [userData, courseId]);
  return (
    <>
      {userData && course && (
        <div className="course-reports-cards">
          <div className="course-reports-cards-card d-table">
            <div className="d-table-row">
              <div className="d-table-cell">Total Lessons:</div>
              <div className="d-table-cell">{course?.lessonsCount}</div>
            </div>
            <div className="d-table-row">
              <div className="d-table-cell">Current Price:</div>
              <div className="d-table-cell">
                {formatter
                (calculateDiscountedPrice(
                  course?.coursePrice,
                  course?.courseDiscount
                ))}
              </div>
            </div>
            <div className="d-table-row">
              <div className="d-table-cell">Course Discount</div>
              <div className="d-table-cell">{course?.courseDiscount}</div>
            </div>
            <div className="d-table-row">
              <div className="d-table-cell">Total durations</div>
              <div className="d-table-cell">{course?.totalDuration}</div>
            </div>
          </div>
          <div className="course-reports-cards-card">
            {!isLoadingForTotalStudent ? (
              <div className="text-center">
                <h1 className="reports-course-card-big-number">
                  {totalStudentEnrolled}
                </h1>
                <p className="mb-0 text-secondary ">
                  Number of students enrolled
                </p>
              </div>
            ) : (
              clipLoader
            )}
          </div>
          <div className="course-reports-cards-card">
            {!isLoadingForMonthlyRevenue ? (
              <div className="text-center">
                <h1 className="reports-course-card-big-number">
                  <span className="text-secondary">$</span>
                  {monthlyRevenue ? formatter(monthlyRevenue) : 0}
                </h1>
                <p className="mb-0 dark-gray ">Revenue this month</p>
              </div>
            ) : (
              clipLoader
            )}
          </div>
        </div>
      )}
    </>
  );
}
