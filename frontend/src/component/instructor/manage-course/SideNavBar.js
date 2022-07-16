import React from "react";
import { NavLink } from "react-router-dom";
import "../../../styles/manage-course/SideNavBar.css";

export default function SideNavBar({ courseid }) {
  const route = `../instructor/course/${courseid}/manage`;
  return (
    <ul className="ol flx">
      <li className="li">
        <div>
          <p className="heading">Plan your course</p>
          <ul className="ol">
            <li className="li">
              <NavLink
                className={({ isActive }) =>
                  isActive ? "active nav-link" : "nav-link"
                }
                to={route + "/course-structure"}
              >
                Course structure
              </NavLink>
            </li>
            <li className="li">
              <NavLink
                className={({ isActive }) =>
                  isActive ? "active nav-link" : "nav-link"
                }
                to={route + "/setup"}
              >
                Setup & test video
              </NavLink>
            </li>
            <li className="li">
              <NavLink
                className={({ isActive }) =>
                  isActive ? "active nav-link" : "nav-link"
                }
                to={route + "/film"}
              >
                Film & edit
              </NavLink>
            </li>
          </ul>
        </div>
      </li>
      <li className="li">
        <div>
          <p className="heading">Publish your course</p>
          <ul className="ol">
            <li className="li">
              <NavLink
                className={({ isActive }) =>
                  isActive ? "active nav-link" : "nav-link"
                }
                to={route + "/description"}
              >
                Course Description
              </NavLink>
            </li>
            <li className="li">
              <NavLink
                className={({ isActive }) =>
                  isActive ? "active nav-link" : "nav-link"
                }
                to={route + "/curriculum"}
              >
                Curriculum
              </NavLink>
            </li>
          </ul>
        </div>
      </li>
    </ul>
  );
}
