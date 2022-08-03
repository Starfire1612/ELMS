import React from "react";
import ReactPlayer from "react-player/youtube";
import "./Player.css";

export default function Player({ videoId, playNext, updateCurrentLessonId }) {
  // const opts = {
  //     height: '100%',
  //     width: '100%',
  //     playerVars: {
  //       autoplay: 0,
  //     },
  //   };
  return (
    <div>
      <ReactPlayer
        className="video-player"
        url={videoId}
        width="100%"
        height="550px"
        controls="true"
        onEnded={playNext}
        onPlay={updateCurrentLessonId}
        playing="true"
      />
      ;
    </div>
  );
}
