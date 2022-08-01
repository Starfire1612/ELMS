import React from 'react'
import '../Styles/Card.css';
import { CameraVideo, Files, PeopleFill, PhoneFill, Trophy } from 'react-bootstrap-icons';
import Button from 'react-bootstrap/Button';


export default function EnrollNow(props) {
  return (
    <div className="courseview-card-body">
      <Button  className='course-enroll type-1 mb-5'>Enroll Now</Button>
        <div className='card-head'>This Course includes : </div>
        <div>
          <div className='card-list'>
            <li><span className='me-3'><CameraVideo/></span>{props.course.courseDuration} hours on-demand video</li>
            <li><span className='me-3'><Files/></span>{props.course.totalLessons} Lessons</li>
            
            <li><span className='me-3'><PeopleFill/></span>Best Instructor</li>
            <li><span className='me-3'><PhoneFill/></span>Access on Mobile</li>
            <li><span className='me-3'><Trophy/></span>Certificate of Completion</li>
          </div>
        </div>
      </div>
  )
}
