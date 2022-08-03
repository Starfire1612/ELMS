import React, { useEffect, useState } from "react";
import { Trophy } from "react-bootstrap-icons";
import { CircularProgressbarWithChildren } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import "./Progressbar.css";

function Progressbar(props) {
  return (
    <div className="app">
      <div className="size-bar">
        <CircularProgressbarWithChildren strokeWidth={7} value={props.progress}>
          <Trophy className="trophy-sie"></Trophy>
        </CircularProgressbarWithChildren>
      </div>
    </div>
  );
}

export default Progressbar;
