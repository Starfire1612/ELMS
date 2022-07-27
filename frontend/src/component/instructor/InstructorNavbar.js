import React from "react";
import { NavDropdown, Nav } from "react-bootstrap";
import { ClipboardData } from "react-bootstrap-icons";
import { Link } from "react-router-dom";

export default function InstructorNavbar({ handleLogout, instructorName }) {
  return (
    <div className="nav-bar justify-content-between instructor-navbar">
      <div>
        <Link to="../instructor">
          <span className="navbar-brand">ELMS LOGO</span>
        </Link>
      </div>
      <div className="d-flex align-items-center instructor-navbar-right-div">
        <Link className="mx-2" to="../instructor/reports">
          <span className="me-2">
            <ClipboardData />
          </span>
          Reports
        </Link>

        <NavDropdown
          className="navbar-user-name"
          title={instructorName ? instructorName : "Instructor's name"}
          menuVariant="dark"
          align="end"
        >
          <div className="p-1">
            <div className="text-center dark-gray mt-0 dropdown-menu-link">
              <Link to="/profile">Profile</Link>
            </div>
            <div
              className="text-center mt-2 border-top border-secondary dropdown-menu-link dark-gray"
              onClick={handleLogout}
            >
              Sign out
            </div>
          </div>
        </NavDropdown>
      </div>
    </div>
  );
}
