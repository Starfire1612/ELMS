import React from "react";
import "../Styles/ShowFeedback.css";
import { Rating } from "react-simple-star-rating";

export default function ShowFeedback(props) {
  return (
    <div className="pt-4">
      <h2 style={{ fontWeight: "bold", color: "black" }}>Student Feedback</h2>
        <div>
        {
        props.feedbacks?.map((feedback) => {
          return (
            <div className="rating-content">
              <div className="circular me-4 fw-bold fs-5">{feedback.studentName[0]}</div>
              <div>
                <div>
                  <span className="fw-semibold fs-5">{feedback.studentName}</span>
                </div>
                <div>
                  <Rating
                    initialValue={feedback.ratings}
                    readonly={true}
                    size={20} 
                  />  
                </div>
                <div>{feedback.content}</div>
              </div>
            </div>
          );
        })
        }
      </div>
      </div>
      
  );
}
