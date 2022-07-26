import React from "react";
import "../../styles/StaticDashboardComponents.css";

export default function StaticDashboardComponents() {
  return (
    <div className="static-components-wrapper">
      <div className="static-component static-component-1">
        <div className="static-component-image-wrapper">
          <img
            className="static-component-image"
            src="https://s.udemycdn.com/instructor/dashboard/engaging-course.jpg"
            alt=""
            width="300"
            height="300"
          />
        </div>
        <div className="static-component-body">
          <h3 className="mb-4 dark-gray">Create an Engaging Course</h3>
          <p>
            Whether you've been teaching for years or are teaching for the first
            time, you can make an engaging course. We've compiled resources and
            best practices to help you get to the next level, no matter where
            you're starting.
          </p>
        </div>
      </div>
      <div className="static-component static-component-2">
        <div className="static-component-image-wrapper">
          <img
            className="static-component-image"
            src="https://s.udemycdn.com/instructor/dashboard/video-creation.jpg"
            alt=""
            width="300"
            height="300"
          />
        </div>
        <div className="static-component-body">
          <h3 className="mb-4 dark-gray">Get Started with Video</h3>
          <p>
            Quality video lectures can set your course apart. Use our resources
            to learn the basics.
          </p>
        </div>
      </div>
      <div className="static-component static-component-2">
        <div className="static-component-image-wrapper">
          <img
            className="static-component-image"
            src="https://s.udemycdn.com/instructor/dashboard/build-audience.jpg"
            alt=""
            width="300"
            height="300"
          />
        </div>
        <div className="static-component-body">
          <h3 className="mb-4 dark-gray">Build Your Audience</h3>
          <p>Set your course up for success by building your audience.</p>
        </div>
      </div>
      <div className="static-component static-component-1">
        <div className="static-component-image-wrapper">
          <img
            className="static-component-image"
            src="https://s.udemycdn.com/instructor/dashboard/newcomer-challenge.jpg"
            alt=""
            width="300"
            height="300"
          />
        </div>
        <div className="static-component-body">
          <h3 className="mb-4 dark-gray">Join the Newcomer Challenge!</h3>
          <p>
            Get exclusive tips and resources designed to help you launch your
            first course faster! Eligible instructors who publish their first
            course on time will receive a special bonus to celebrate. Start
            today!
          </p>
        </div>
      </div>
    </div>
  );
}
