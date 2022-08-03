import React from "react";
import { Button, Card } from "react-bootstrap";
import "../../styles/CoursesLoadingAnimation.css";

function CoursesLoadingAnimation() {
  const card = () => {
    return (
      <Card className="card" style={{ width: "18rem" }}>
        <Card.Img
          variant="top"
          src="https://img-c.udemycdn.com/user/200_H/anonymous_3.png"
        />
        <div className="bgcolor  image"></div>
        <Card.Body>
          <Card.Title className="mb-1 mt-2 bgcolor animated-bg"></Card.Title>
          <p className="fs-6 mb-3 fw-light instructor-name bgcolor animated-bg"></p>
          <Card.Text>
            <div className="description">
              <div className="animated-bg"></div>
              <div className="animated-bg"></div>
              <div className="animated-bg"></div>
            </div>
          </Card.Text>
          <div className="d-flex justify-content-between align-items-center">
            <Button variant="success" disabled>
              <div className="button"></div>
            </Button>
            <div className="ratings bgcolor animated-bg"></div>
          </div>
        </Card.Body>
      </Card>
    );
  };

  return (
    <div className="courses">
      {card()}
      {card()}
      {card()}
      {card()}
      {card()}
      {card()}
      {card()}
      {card()}
      {card()}
      {card()}
    </div>
  );
}

export default CoursesLoadingAnimation;
