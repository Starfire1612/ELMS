import React from 'react'
import '../Styles/Course_details.css'
export default function WhatLearn() {
  const learn=['You will build 16 web development projects for your portfolio','ready to apply for junior developer jobs',
  'You will learn the latest technologies',' including Javascript, React',
  'You will master both front and back-end development',' becoming a full-stack developer by the end of the course.',
  'After the course you will be able to build ANY website you want.',
  'Build fully-fledged websites and web apps for your startup or business.',
  'Work as a freelance web developer.',
  'Learn professional developer best practices.'];
  return (
    
        <div className='block-lesson'>
            
                <h1 className='lesson-head'>What you'll learn?</h1>           
                <ul  className='lesson-items'>
                {
                learn.map((a,index)=>{
                    return(
                      <li key={index}>
                        {a}
                      </li>
                    )
                  })
                }</ul>
        </div>
  )
}
