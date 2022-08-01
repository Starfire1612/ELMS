import React from 'react'
import '../Styles/Course_details.css'
export default function Instructor( props) {
  return (
    <div className='instructor-head'>
      <h2 style={{fontWeight:'bold',color:'black'}}>Instructor</h2>
      <div><p className='fs-5 mt-5'>{props.instructorName}</p></div>
      {/* <div><h3>Contact at {props.instructor.instructorEmail}</h3></div> */}
    </div>
  )
}
