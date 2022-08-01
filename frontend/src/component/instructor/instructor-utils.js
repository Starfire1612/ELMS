import axios from "axios";
const BASE_URL = "http://localhost:8100/instructor/";
const config = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  },
};
export const getPublishedCourses = async (id) => {
  return await axios
    .get(`${BASE_URL}${id}/courses`, config)
    .then((response) => response.data)
    .catch((error) => console.log(error));
};

export const getCourseDetails = async (id, cid) => {
  return await axios
    .get(`${BASE_URL}${id}/courses/${cid}`, config)
    .then((response) => response.data)
    .catch((error) => console.log(error));
};

export const createCourse = async (id, course) => {
  return await axios
    .post(`${BASE_URL}${id}/create-course`, course, config)
    .then((response) => response.status)
    .catch((error) => console.log(error));
};

export const postCoursePic = async (id, cid, imageFile) => {
  console.log("Instructor Id:", id, "Course Id:", cid, "ImageFile", imageFile);
  const formData = new FormData();
  formData.append("file", imageFile);

  const result = await fetch(
    `http://localhost:8100/instructor/${id}/course/${cid}/uploadCourseImage`,
    {
      method: "PATCH",
      body: formData,
      headers: {
        Authorization: `Bearer ${localStorage.getItem("userToken")}`,
      },
    }
  )
    .then((response) => response.json())
    .then((result) => {
      const imageUrl = "data:image/png;base64," + result.courseImage;
      console.log("Your image url", imageUrl);
      return result;
    })
    .catch((error) => {
      console.error("Error:", error);
    });
  return result;
};

export const updateCourse = async (id, course) => {
  return await axios
    .patch(`${BASE_URL}${id}/create-course`, course, config)
    .then((response) => response.status)
    .catch((error) => console.log(error));
};

export const getCourseLessons = async (id, cid) => {
  return await axios
    .get(`${BASE_URL}${id}/courses/${cid}`, config)
    .then((response) => response.data.lessons)
    .catch((error) => console.log(error));
};

export const updateLesson = async (id, cid, lessonId, lesson) => {
  return await axios
    .patch(`${BASE_URL}${id}/course/${cid}/lessons/${lessonId}`, lesson, config)
    .then((response) => response)
    .catch((error) => console.log(error));
};
export const deleteLesson = async (id, cid, lessonId) => {
  return await axios
    .delete(`${BASE_URL}${id}/course/${cid}/lessons/${lessonId}`, config)
    .then((response) => response)
    .catch((error) => console.log(error));
};
export const updateCourseLessons = async (id, cid, lessons) => {
  return await axios
    .post(`${BASE_URL}${id}/course/${cid}/lessons`, lessons, config)
    .then((response) => response)
    .catch((error) => console.log(error));
};
