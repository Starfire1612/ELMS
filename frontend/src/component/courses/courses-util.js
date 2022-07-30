import axios from "axios";

const BASE_URL = "http://localhost:8100";
const config = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  },
};
export const getAllPublishedCourse = async (id, page) => {
  return await axios
    .get(`${BASE_URL}/student/${id}/published-courses/${page}`, config)
    .then((response) => response.data)
    .catch((error) => error);
};

export const getCourseDetails = async (courseId, id) => {
  // console.log("Index:"+page)
  return await axios
    .get(`${BASE_URL}/student/${id}/courses/${courseId}/courseDetails`, config)
    .then((response) => response.data)
    .catch((error) => error);
};

export const enrollStudentInCourse = async (
  courseId,
  studentId,
  paymentResponseBody
) => {};
