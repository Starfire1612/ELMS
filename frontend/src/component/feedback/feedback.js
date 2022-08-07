import { useState, React, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Rating } from "react-simple-star-rating";
import {
  isFeedbackPresent,
  postEditFeedback,
} from "../courses/courses-util.js";
import { Button } from "react-bootstrap";
import { deleteFeedback } from "./../courses/courses-util";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants.js";

export default function Feedback() {
  const params = useParams();
  const studentId = params.studentId;
  const courseId = params.courseId;

  const [feedback, setFeedback] = useState({});
  const [existingFeedback, setexistingFeedback] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const [rating, setRating] = useState(0); // initial rating value

  // Catch Rating value
  const handleRating = (rate) => {
    setRating(rate);
  };

  const handleChange = (event) => {
    setFeedback((prevState) => ({
      ...prevState,
      [event.target.name]: event.target.value,
    }));
  };

  const handleDelete = async () => {
    await deleteFeedback(studentId, courseId);
  };

  const checkFeedbackExists = async () => {
    setIsLoading(true);
    const response = await isFeedbackPresent(studentId, courseId);
    console.log("FEEDBCAK RESOPNSE", response);
    if (response) {
      setFeedback(response);
      setexistingFeedback(true);
    }
    setIsLoading(false);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const feedbackObject = {
      content: feedback.content,
      ratings: rating / 20,
    };
    console.log("Feedback Object:", feedbackObject);
    // const response = await axios.post("http://localhost:8080/feedback/student/save",userData);
    const response = await postEditFeedback(
      studentId,
      courseId,
      feedbackObject
    );
    console.log(response);
  };

  useEffect(() => {
    checkFeedbackExists();
  }, []);

  return (
    <div style={styles.container}>
      {/* <h2> Feedback </h2> */}
      {!isLoading && feedback ? (
        <>
          <div>
            <div className="rating-content" key={feedback.feedbackId}>
              <div className="circular me-4 fw-bold fs-5">
               {feedback.studentName?feedback.studentName[0]:"A"}
              </div>
              <div>
                <div>
                  <span className="fw-semibold fs-5">
                    {feedback.studentName}
                  </span>
                </div>
                <div>
                  <Rating
                    onClick={handleRating}
                    initialValue={existingFeedback ? feedback.ratings : 1}
                    ratingValue={rating}
                  />
                </div>
                <textarea
                  name="content"
                  onChange={handleChange}
                  placeholder="What's your experience?"
                  defaultValue={
                    existingFeedback ? feedback.content : "Informative"
                  }
                  style={styles.textarea}
                />
                <div style={styles.buttonsContainer}>
                  {existingFeedback ? (
                    <div>
                      <Button onClick={handleSubmit} style={styles.button}>
                        Edit{" "}
                      </Button>
                      <Button onClick={handleDelete} style={styles.button}>
                        Delete{" "}
                      </Button>
                    </div>
                  ) : (
                    <Button onClick={handleSubmit} style={styles.button}>
                      Submit{" "}
                    </Button>
                  )}
                </div>
              </div>
            </div>
          </div>
        </>
      ) : (
        <>
          <ClipLoader color={LOADING_COLOR} size="50px" />
        </>
      )}
    </div>
  );
}

const styles = {
  container: {
    display: "flex",
    flexDirection: "column",
    align: "left",
  },
  textarea: {
    border: "1px solid #a9a9a9",
    borderRadius: 5,
    padding: 10,
    marginBlock:"10px",
    minHeight: "150px",
    width: "100%",
  },

  button: {
    border: "1px solid #a9a9a9",
    borderRadius: 5,
    width: 150,
    maxWidth: 200,
    padding: 10,
    backgroundColor:"purple",
  },
  buttonsContainer: {
    display: "flex",
    flexDirection:"row",
    align: "left"
  },
};
