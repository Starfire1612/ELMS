import axios from "axios";
import bcryptjs from "bcryptjs";

const REGISTRATION_URL="http://localhost:8000";
const AUTHENTICATION_URL="http://localhost:8080";

export const postRegisteredUser = async (userType, userData) => {
  if (userType === "student") {
    userData.studentPassword = bcryptjs.hashSync(userData.studentPassword, 10);
    return await axios
      .post(`${REGISTRATION_URL}/register-user/type/${userType}`, userData)
      .then((response) => response.data)
      .catch(error=>console.log(error));
  } else {
    userData.instructorPassword = bcryptjs.hashSync(userData.instructorPassword, 10);
    return await axios
      .post(`${REGISTRATION_URL}/register-user/type/${userType}`, userData)
      .then((response) => response.data)
      .catch(error=>console.log(error));
  }
};
  
  export const getEmailVerificationMail = async (userType, userEmail) => {
    return await axios
      .post(`${REGISTRATION_URL}/registration/email/${userEmail}/type/${userType}`)
      .then((response) => response.status)
      .catch(error=>console.log(error));
}

export const postAuthenticatedUser = async (userData) => {
  return await axios
    .post(`${AUTHENTICATION_URL}/authenticate`,
    userData)
    .then((response) => response.data.jwttoken)
    .catch(error=>console.log(error)); //returns status code
}

export const sendForgotPasswordMail = async (userType, userEmail) => {
  return await axios
    .get(`${AUTHENTICATION_URL}/forgot-password/email/${userEmail}/type/${userType}`)
    .then((response) => response.headers.Otp); //returns response
}

export const postNewPassword = async (userData) => {
  return await axios
    .patch(`${AUTHENTICATION_URL}/forgot-password`)
    .then((response) => response.status)
    .catch(error=>console.log(error)); //returns response
}
  
