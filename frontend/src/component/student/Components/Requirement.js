import React from 'react'
import '../Styles/Course_details.css'
export default function Requirement() {
  return (
    <div className='requirement'>
      <h2 style={{fontWeight:'bold',color:'black'}}>Requirements</h2>

      <ul className='require ps-4'>
        <li>Some programming experience</li>
        <li>Admin permissions to download files</li>
      </ul>
    </div>
  )
}
