import { useState } from "react";
import { Button } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { List } from "react-bootstrap-icons";
import { useMediaQuery } from "react-responsive";
import "../../../styles/manage-course/SideNavBar.css";

export default function SideNavBar({ manageDeleteCourse ,courseId}) {
  const route = `../instructor/course/${courseId}/manage`;
  const [isActive, setIsActive] = useState(false);
  const shouldSideNavRender = useMediaQuery({
    query: "(min-width: 900px)",
  });

  const handleSideNavbar = () => {
    setIsActive(!isActive);
  };

  return (
    <div className="position-sticky fixed-side-nav">
      <button
        onClick={handleSideNavbar}
        className="bg-white side-nav-btn border-0"
      >
        <List height="2em" width="2em" />
      </button>
      {(shouldSideNavRender || isActive) && (
        <ul className="ol flx">
          <li className="li">
            <div>
              <p className="heading">Plan your course</p>
              <ul className="ol">
                <li className="li">
                  <NavLink
                    className={({ isActive }) =>
                      isActive
                        ? "manage-course-side-navbar-active nav-link"
                        : "nav-link"
                    }
                    to={route + "/course-structure"}
                  >
                    Course structure
                  </NavLink>
                </li>
                <li className="li">
                  <NavLink
                    className={({ isActive }) =>
                      isActive
                        ? "manage-course-side-navbar-active nav-link"
                        : "nav-link"
                    }
                    to={route + "/setup"}
                  >
                    Setup & test video
                  </NavLink>
                </li>
                <li className="li">
                  <NavLink
                    className={({ isActive }) =>
                      isActive
                        ? "manage-course-side-navbar-active nav-link"
                        : "nav-link"
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
                      isActive
                        ? "manage-course-side-navbar-active nav-link"
                        : "nav-link"
                    }
                    to={route + "/description"}
                  >
                    Course Description
                  </NavLink>
                </li>
                <li className="li">
                  <NavLink
                    className={({ isActive }) =>
                      isActive
                        ? "manage-course-side-navbar-active nav-link"
                        : "nav-link"
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
      )}
      <div className="delete-course">
        <Button
          className="delete-course-btn type-1"
          onClick={manageDeleteCourse}
        >
          Delete Course
        </Button>
      </div>
    </div>
  );
}
