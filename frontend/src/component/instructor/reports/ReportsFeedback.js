import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getAllCourseFeedbacksByRatings } from "../instructor-utils";
import Form from "react-bootstrap/Form";
import { Rating } from "react-simple-star-rating";

export default function ReportsFeedback({ userData }) {
  const params = useParams();
  const courseId = params.courseId;
  const [isLoading, setIsLoading] = useState(false);
  const [feedbackList, setFeedbackList] = useState([]);
  const [searchRatings, setSearchRatings] = useState();
  const [hasFeedback, setHasFeedback] = useState(true);

  const fetchFeedbacksByRatings = async () => {
    setIsLoading(false);
    const response = await getAllCourseFeedbacksByRatings(
      userData.instructorId,
      courseId,
      searchRatings
    );
    console.log("Feedbacks containg 5 star ratings are", response);
    if (!response.length) {
      setHasFeedback(false);
      return;
    }
    setFeedbackList(response);
    setHasFeedback(true);
    setIsLoading(false);
  };
  const handleChange = (event) => {
    setSearchRatings(event.target.value);
  };

  useEffect(() => {
    if (!searchRatings) return;
    fetchFeedbacksByRatings();
  }, [searchRatings]);
  return (
    <div className="reports-feedback-container">
      <div className="d-flex align-items-center">
        <h4 className="flex-grow-1 mb-0">Feedbacks</h4>
        <Form.Select
          className="search-via-ratings"
          name="rating"
          onChange={handleChange}
          disabled={isLoading}
        >
          <option value="">Select one</option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </Form.Select>
      </div>
      <div className="reports-feedback-list mt-3">
        {hasFeedback ? (
          feedbackList.map((feedback) => (
            <div className="reports-feedback" key={feedback.feedbackId}>
              <div className="d-inline-block">
                <div className="circular me-4 fw-bold fs-5">
                  {feedback.studentName[0]}
                </div>
              </div>
              <div className="fw-semibold fs-5 d-inline">
                {feedback.studentName}
              </div>

              <div className="mt-3">
                <Rating
                  initialValue={feedback.ratings}
                  readonly={true}
                  size={20}
                />
              </div>
              <div className="mt-3">{feedback.content}</div>
            </div>
          ))
        ) : (
          <div className="text-center dart-gray">No feedbacks</div>
        )}
      </div>
    </div>
  );
}
