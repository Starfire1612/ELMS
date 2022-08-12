import axios from "axios";

const BASE_URL = "http://localhost:8765";
const config = () => {
  return {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    },
  };
};

export const getAllPublishedCourse = async (id, page) => {
  console.log("Calling get allcourse from course util for student");

  if (!id) {
    console.log(id, page);
    return;
  }
  // const config = {
  //   headers: {
  //     Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  //   },
  // };
  return await axios
    .get(`${BASE_URL}/student/${id}/published-courses/${page}`, config())
    .then((response) => response.data)
    .catch((error) => error);
};

export const getCourseDetails = async (courseId, id) => {
  // console.log("Index:"+page)
  if (!id || !courseId) return;
  return await axios
    .get(
      `${BASE_URL}/student/${id}/course/${courseId}/view-course-details`,
      config()
    )
    .then((response) => response.data)
    .catch((error) => console.log(error));
};

export const getStudentEnrolledCourses = async (sid) => {
  // console.log("Index:"+page)
  if (!sid) return;
  return await axios
    .get(`${BASE_URL}/student/${sid}/enrolled-courses`, config())
    .then((response) => response.data)
    .catch((error) => console.log(error));
};

export const getSearchedCourses = async (sid, searchQuery) => {
  // console.log("Index:"+page)
  if (!sid || !searchQuery) return;
  return await axios
    .get(`${BASE_URL}/student/${sid}/search/${searchQuery}`, config())
    .then((response) => response.data)
    .catch((error) => console.log(error));
};

export const enrollStudentInCourse = async (
  id,
  courseId,
  paymentResponseBody
) => {
  if (!id || !courseId || !paymentResponseBody) return;
  return await axios
    .post(
      `${BASE_URL}/student/${id}/course/${courseId}/enroll`,
      paymentResponseBody,
      config()
    )
    .then((response) => response.data)
    .catch((error) => console.log(error));
};
export const getEnrolledStudentCourseDetails = async (id, cid) => {
  if (!id || !cid) return;
  return await axios
    .get(`${BASE_URL}/student/${id}/courses/${cid}/courseDetails`, config())
    .then((response) => response.data)
    .catch((error) => console.log(error));
};
//this method will generate certificate
//Must send any object. String is converted into
//Content type 'application/x-www-form-urlencoded;charset=UTF-8'
// which is not supported in backend
export const updateStudentCourseCurrentLesson = async (
  id,
  cid,
  lid,
  lesson
) => {
  if (!id || !cid || !lid || !lesson) return;
  return await axios
    .patch(
      `${BASE_URL}/student/${id}/course/${cid}/lesson/${lid}`,
      lesson,
      config()
    )
    .then((response) => console.log(response.data))
    .catch((error) => console.log(error));
};

//this method will be called for every video watched by the user
export const addLessonInStudentCourse = async (id, cid, lid) => {
  if (!id || !cid || !lid) return;
  return await axios
    .post(
      `${BASE_URL}/student/${id}/course/${cid}/lesson/${lid}`,
      "doesNotMatter",
      config()
    )
    .then((response) => response.data)
    .catch((error) => console.log(error));
};

export const sendCertificateCompletionMail = async (sid, cid) => {
  return await axios
    .get(`${BASE_URL}/student/${sid}/course/${cid}/certficate`, config())
    .then((response) => response.status)
    .catch((error) => error.response.status);
};

export const postEditFeedback = async (sid, cid, feedback) => {
  return await axios
    .post(
      `${BASE_URL}/student/${sid}/course/${cid}/feedback`,
      feedback,
      config()
    )
    .then((response) => console.log(response.data))
    .catch((error) => console.log(error));
};

export const deleteFeedback = async (sid, cid) => {
  return await axios
    .delete(`${BASE_URL}/student/${sid}/course/${cid}/feedback`, config())
    .then((response) => console.log(response.data))
    .catch((error) => console.log(error));
};

export const isFeedbackPresent = async (sid, cid) => {
  return await axios
    .get(`${BASE_URL}/student/${sid}/course/${cid}/feedback`, config())
    .then((response) => response.data)
    .catch((error) => console.log(error));
};
