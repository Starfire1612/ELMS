import React from 'react'

export default function HeaderSection(props) {
  return (
    <div className='header-container'>
        <div className='header-title'>{props.title}</div>
        <div className='header-subtitle'> {props.subtitle}</div>
    </div>
  )
}
