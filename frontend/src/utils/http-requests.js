import axios from "axios";
// export default axios.create({
//   baseURL: "http://localhost:8000",
//   headers: {
//     "Content-type": "application/json"
//   }
// });

const fetchOtp = async (email, type) => {
  return await axios
    //request to send and fetch OTP.(API has to be implemented and created in the back end)
    .get(`http://localhost:8080/forgot-password/email/${email}/type/${type}`)
    .then((response) => response.headers.otp)
    .catch((err) => console.log(err));
};

export { fetchOtp };
