import React from "react";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "./utils/constants";

export default function DummyComponent() {
  return (
    <div className="course-list">
      <ClipLoader color={LOADING_COLOR} size="50px" />
      <p className="font-monspace text-secondary"> Hang in there</p>
    </div>
  );
}
