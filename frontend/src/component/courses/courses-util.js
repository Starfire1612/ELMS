import axios from "axios";

const BASE_URL = "http://localhost:8100";
const config = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  },
};
export const getAllPublishedCourse = async (id, page) => {
  if (!id) return;
  return await axios
    .get(`${BASE_URL}/student/${id}/published-courses/${page}`, config)
    .then((response) => response.data)
    .catch((error) => error);
};

export const getCourseDetails = async (courseId, id) => {
  // console.log("Index:"+page)
  return await axios
    .get(
      `${BASE_URL}/student/${id}/course/${courseId}/view-course-details`,
      config
    )
    .then((response) => response.data)
    .catch((error) => console.log(error));
};

export const getStudentEnrolledCourses = async (sid) => {
  // console.log("Index:"+page)
  return await axios
    .get(`${BASE_URL}/student/${sid}/enrolled-courses`, config)
    .then((response) => response.data)
    .catch((error) => console.log(error));
};

export const getSearchedCourses = async (sid, serachQuery) => {
  // console.log("Index:"+page)
  return await axios
    .get(`${BASE_URL}/student/${sid}/search/${serachQuery}`, config)
    .then((response) => response.data)
    .catch((error) => console.log(error));
};

export const enrollStudentInCourse = async (
  id,
  courseId,
  paymentResponseBody
) => {
  return await axios
    .post(
      `${BASE_URL}/student/${id}/course/${courseId}/enroll`,
      paymentResponseBody,
      config
    )
    .then((response) => response.data)
    .catch((error) => console.log(error));
};
