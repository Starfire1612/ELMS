export const postProfileDetails = async (userType, userData) => {
  switch (userType) {
    case "student": {
      return await fetch(
        `http://localhost:8765/student/${userData.studentId}/profile`,
        {
          method: "PATCH",
          body: JSON.stringify(userData),
          headers: {
            Authorization: `Bearer ${localStorage.getItem("userToken")}`,
            "Content-Type": "application/json",
          },
        }
      )
        .then((response) => response.status)
        .catch((error) => {
          console.error("Error:", error);
        });
      break;
    }
    case "instructor": {
      return await fetch(
        `http://localhost:8765/instructor/${userData.instructorId}/profile`,
        {
          method: "PATCH",
          body: JSON.stringify(userData),
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("userToken")}`,
          },
        }
      )
        .then((response) => response.status)
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

  switch (userType) {
    case "student": {
      const result = await fetch(
        `http://localhost:8765/student/${userData.studentId}/uploadProfilePic`,
        {
          method: "PATCH",
          body: formData,
          headers: {
            Authorization: `Bearer ${localStorage.getItem("userToken")}`,
          },
        }
      )
        .then((response) => response.json())
        .catch((error) => {
          console.error("Error:", error);
        });
      return result;
    }
    case "instructor": {
      const result = await fetch(
        `http://localhost:8765/instructor/${userData.instructorId}/uploadProfilePic`,
        {
          method: "PATCH",
          body: formData,
          headers: {
            Authorization: `Bearer ${localStorage.getItem("userToken")}`,
          },
        }
      )
        .then((response) => response.json())
        .catch((error) => {
          console.error("Error:", error);
        });
      return result;
    }
    default:
  }
};
