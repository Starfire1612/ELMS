import axios from "axios";
import { youtubeParser } from "./util";
import { GOOGLE_API_KEY } from "./constants";

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

export { getVideoDuration };
