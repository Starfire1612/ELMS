import React from 'react'
import '../Styles/Course_details.css';
export default function CourseContent(props) {
   
      
  return (
    <div className='main-head-course-content'>
      <h3 style={{ fontWeight:'bold'}}>Course Content</h3>
      <div className='course-content-head '>
        <div>
        {props.course.totalLessons} Lessons  •  {props.course.courseDuration} Hours
        </div>
      </div>
      <div className='course-content'>
      
                
                 <div className='lesson-desc'> 
                 {props.course.lessons.map((lesson,index)=>{
                    return(
                      <div className='lesson-content d-flex justify-content-between align-items-center px-5'>
                        <div><span className='me-3'>{index+1}</span><span className='fw-semibold'>{lesson.lessonName}</span></div>
                        <div>{lesson.lesonDuration} mins</div>
                      </div>
                      
                    )
                  })
                }</div>
      </div>
      
    </div>
  )
}
