import React from 'react'
import '../Styles/Course_details.css'
export default function Description(props) {
  return (
    <div className='desc'>
      <h2 style={{fontWeight:'bold',color:'black'}}>Description</h2>
      <div className='desc-content'>{props.desc}</div>
    </div>
  )
}
