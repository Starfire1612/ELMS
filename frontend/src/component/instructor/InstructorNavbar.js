import React from "react";
import { NavDropdown } from "react-bootstrap";
import {
  BoxArrowLeft,
  ClipboardData,
  PersonCircle,
} from "react-bootstrap-icons";
import { Link } from "react-router-dom";

export default function InstructorNavbar({ handleLogout, userData }) {
  return (
    <div className="nav-bar nav-bar-dark justify-content-between instructor-navbar">
      <div>
        <Link to="../instructor">
          <span className="navbar-brand">ELMS LOGO</span>
        </Link>
      </div>
      <div className="d-flex align-items-center navbar-right-div">
        <Link className="mx-2" to="../instructor/reports">
          <span className="me-2">
            <ClipboardData />
          </span>
          Reports
        </Link>

        <NavDropdown
          className="navbar-user-name"
          title={userData ? userData.instructorName : "Instructor's name"}
          menuVariant="dark"
          align="end"
        >
          <div className="p-1">
            <div className="text-center dark-gray mt-0 dropdown-menu-link">
              <Link to="/profile">
                <PersonCircle className="me-2" />
                Profile
              </Link>
            </div>
            <div
              className="text-center mt-2 border-top border-secondary dropdown-menu-link dark-gray"
              onClick={handleLogout}
            >
              <BoxArrowLeft className="me-2" />
              Sign out
            </div>
          </div>
        </NavDropdown>
      </div>
    </div>
  );
}
