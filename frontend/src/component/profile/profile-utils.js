import { axios } from "axios";

export const postProfileDetails = async (userType, userData) => {
  //axios call
  const BASE_URL = "";
  console.log(userData);
  switch (userType) {
    case "student": {
      await fetch("http://localhost:8100/student/4/profile", {
        method: "PATCH",
        body: JSON.stringify(userData),
        headers: {
          Authorization:
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYWRoaWthc2hhaDE2MTJAZ21haWwuY29tIiwidXNlclR5cGUiOiJzdHVkZW50IiwiZXhwIjoxNjU5MTg1MDkzLCJpYXQiOjE2NTkwOTg2OTN9.ZMfiOZ75ZGer52twf19rAO9jW3hEXQHKfrMtKuZ3mEyeXAKsmVjT8oemihYODAyNQVtcRqqAbEtSU2BNse-1Yw",
          "Content-Type": "application/json",
        },
      })
        .then((response) => response.data)
        .then((result) => {
          console.log("Success:", result);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
      break;
    }
    case "instructor": {
      await fetch("http://localhost:8100/instructor/1/profile", {
        method: "PATCH",
        body: JSON.stringify(userData),
        headers: {
          "Content-Type": "application/json",
          Authorization:
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYWRoaWthc2hhaDE2MTJAZ21haWwuY29tIiwidXNlclR5cGUiOiJpbnN0cnVjdG9yIiwiZXhwIjoxNjU5MTU4MzE3LCJpYXQiOjE2NTkwNzE5MTd9.qu8yQwgKcVLaiA8SBgABxXBnqbgPMlBmB7bJLAo7_HFqpYJDSyIaYMaex4pVB60_PokC37anbknVIOhPmGTRsg",
        },
      })
        .then((response) => console.log(response))
        .then((result) => {
          // console.log('Success:', result);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
      break;
    }
    default:
  }
};

export const postProfilePic = async (userType, userData, imageFile) => {
  //axios cal
  console.log(userType, userData, imageFile);
  const formData = new FormData();
  formData.append("file", imageFile);
  //   const BASE_URL = "http://localhost:8100/";
  // const config = {
  //   headers: {
  //     "content-type": "multipart/form-data",
  //     "Authorization":
  //       "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYWRoaWthc2hhaDE2MTJAZ21haWwuY29tIiwidXNlclR5cGUiOiJzdHVkZW50IiwiZXhwIjoxNjU5MTg1MDkzLCJpYXQiOjE2NTkwOTg2OTN9.ZMfiOZ75ZGer52twf19rAO9jW3hEXQHKfrMtKuZ3mEyeXAKsmVjT8oemihYODAyNQVtcRqqAbEtSU2BNse-1Yw" },
  // };
  switch (userType) {
    case "student": {
      const result = await fetch(
        "http://localhost:8100/student/4/uploadProfilePic",
        {
          method: "PATCH",
          body: formData,
          headers: {
            "Authorization":
              "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYWRoaWthc2hhaDE2MTJAZ21haWwuY29tIiwidXNlclR5cGUiOiJzdHVkZW50IiwiZXhwIjoxNjU5MTg1MDkzLCJpYXQiOjE2NTkwOTg2OTN9.ZMfiOZ75ZGer52twf19rAO9jW3hEXQHKfrMtKuZ3mEyeXAKsmVjT8oemihYODAyNQVtcRqqAbEtSU2BNse-1Yw"
          }
        }
      )
        .then((response) => response.json())
        .then((result) => {
          // console.log('Success:', result);
          //or
          // let str = res;
          // let buff = new Buffer(str, 'base64');
          // let base64ToStringNew = buff.toString('ascii');
          const imageUrl = "data:image/png;base64," + result.studentImage;
          localStorage.setItem("userImage", imageUrl);
          console.log("Your image url", imageUrl);
          return result;
        })
        .catch((error) => {
          console.error("Error:", error);
        });
      return result;
      break;
    }
    case "instructor": {
      //   await axios
      //     .put(
      //       `${BASE_URL}/instructor/${userData.studentId}/uploadProfilePic`,
      //       formData,
      //       config
      //     )
      //     .then((response) => console.log(response.data));
      break;
    }
    default:
  }
};
