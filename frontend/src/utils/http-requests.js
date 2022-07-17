import axios from "axios";
import { youtubeParser } from "./util";
import { GOOGLE_API_KEY } from "./constants";

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

const getVideoDuration = async (url) => {
  const videoId = youtubeParser(url);
  if (!videoId) return false;
  return await axios
    .get(
      `https://www.googleapis.com/youtube/v3/videos?part=contentDetails&id=${videoId}&key=${GOOGLE_API_KEY}`
    )
    .then((res) => res.data.items)
    .then((items) => {
      if (!items) return;
      return items[0].contentDetails.duration;
    })
    .catch((err) => console.log(err));
};

export { fetchOtp, getVideoDuration };
