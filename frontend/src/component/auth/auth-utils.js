import axios from "axios";
import bcryptjs from "bcryptjs";

const REGISTRATION_URL = "http://localhost:8765";
const AUTHENTICATION_URL = "http://localhost:8765";

export const postRegisteredUser = async (userType, userData) => {
  userData.password = bcryptjs.hashSync(userData.password, 10);
  return await axios
    .post(`${REGISTRATION_URL}/register-user/type/${userType}`, userData)
    .then((response) => response.status)
    .catch((error) => error.response.status);
};

export const getEmailVerificationMail = async (userEmail, userType) => {
  return await axios
    .get(`${REGISTRATION_URL}/registration/email/${userEmail}/type/${userType}`)
    .then((response) => response.headers.otp)
    .catch((error) => error.response.status);
};

export const postAuthenticatedUser = async (userData) => {
  return await axios
    .post(`${AUTHENTICATION_URL}/authenticate`, userData)
    .then((response) => response.data.jwttoken)
    .catch((error) => console.log(error)); //returns status code
};

export const sendForgotPasswordMail = async (userEmail, userType) => {
  return await axios
    .get(
      `${AUTHENTICATION_URL}/forgot-password/email/${userEmail}/type/${userType}`
    )
    .then((response) => response.headers.otp)
    .catch((error) => console.log(error)); //returns response
};

export const postNewPassword = async (userData) => {
  userData.password = bcryptjs.hashSync(userData.password, 10);
  return await axios
    .patch(`${AUTHENTICATION_URL}/forgot-password`, userData)
    .then((response) => response.status)
    .catch((error) => error.response.status); //returns response
};
