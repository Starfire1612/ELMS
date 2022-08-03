import React from "react";
import "./LessonList.css";

export default function LessonList({
  handleLessonChange,
  lessonList,
  current,
}) {
  console.log("Current Index:", current);
  return (
    <div>
      {lessonList.map((lesson, index) => {
        return (
          <li key={index}
            className={
              current === index ? "list-items lesson-active" : "list-items"
            }
            onClick={() => handleLessonChange(index)}
          >
            <div>{lesson.lessonName}</div>
            <div>{lesson.lessonDuration}mins</div>
          </li>
        );
      })}
    </div>
  );
}
